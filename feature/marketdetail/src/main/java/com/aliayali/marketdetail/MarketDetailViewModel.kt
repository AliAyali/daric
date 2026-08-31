package com.aliayali.marketdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.common.error.AppError
import com.aliayali.common.result.AppResult
import com.aliayali.domain.GetCoinDetailUseCase
import com.aliayali.domain.GetCoinPriceHistoryUseCase
import com.aliayali.domain.GetMarketAssetDetailUseCase
import com.aliayali.domain.ObserveNetworkConnectivityUseCase
import com.aliayali.domain.sync.MarketSyncer
import com.aliayali.marketdetail.mapper.asUiData
import com.aliayali.marketdetail.mapper.asUiModel
import com.aliayali.marketdetail.model.MarketPricePointUiModel
import com.aliayali.marketdetail.navigation.MarketDetailAssetType
import com.aliayali.marketdetail.navigation.MarketDetailNavKey
import com.aliayali.model.market.MarketPricePoint
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel(
    assistedFactory = MarketDetailViewModel.Factory::class,
)
class MarketDetailViewModel @AssistedInject constructor(
    @Assisted private val navKey: MarketDetailNavKey,
    private val getMarketAssetDetailUseCase: GetMarketAssetDetailUseCase,
    private val getCoinDetailUseCase: GetCoinDetailUseCase,
    private val getCoinPriceHistoryUseCase: GetCoinPriceHistoryUseCase,
    private val observeNetworkConnectivityUseCase: ObserveNetworkConnectivityUseCase,
    private val marketSyncer: MarketSyncer,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MarketDetailUiState>(
        MarketDetailUiState.Loading,
    )

    val uiState: StateFlow<MarketDetailUiState> =
        _uiState.asStateFlow()

    private var chartJob: Job? = null

    init {
        observeNetwork()
        observeAssetDetail()
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            observeNetworkConnectivityUseCase()
                .collect { isOnline ->

                    val wasOffline = _uiState.value
                        .successOrNull()
                        ?.isOffline == true

                    updateNetworkState(isOnline)

                    if (wasOffline && isOnline) {
                        refresh()
                    }
                }
        }
    }

    private fun updateNetworkState(isOnline: Boolean) {
        _uiState.update { state ->
            state.mapSuccess { success ->
                success.copy(
                    isOffline = !isOnline,
                )
            }
        }
    }

    private fun observeAssetDetail() {
        when (navKey.assetType) {
            MarketDetailAssetType.MARKET -> observeMarketAsset()
            MarketDetailAssetType.CRYPTO -> observeCoin()
        }
    }

    private fun observeMarketAsset() {
        viewModelScope.launch {
            getMarketAssetDetailUseCase(
                id = navKey.assetId,
            ).collect { asset ->

                if (asset == null) {
                    setError(AppError.Unknown)
                    return@collect
                }

                _uiState.update { currentState ->
                    MarketDetailUiState.Success(
                        marketDetailUiData = asset.asUiData(),
                        chart = MarketChartUiState.Unavailable,
                        isRefreshing = currentState
                            .successOrNull()
                            ?.isRefreshing
                            ?: false,
                        isOffline = currentState
                            .successOrNull()
                            ?.isOffline
                            ?: false,
                    )
                }
            }
        }
    }

    private fun observeCoin() {
        viewModelScope.launch {
            combine(
                getCoinDetailUseCase(
                    id = navKey.assetId,
                ),
                getMarketAssetDetailUseCase(
                    id = USD_ASSET_ID,
                ),
            ) { coin, dollarAsset ->
                coin to dollarAsset?.price
            }.collect { (coin, dollarToToman) ->

                if (coin == null) {
                    setError(AppError.Unknown)
                    return@collect
                }

                val previousState = _uiState.value.successOrNull()

                val shouldLoadChart =
                    previousState == null ||
                            previousState.chart is MarketChartUiState.Error

                _uiState.update {
                    MarketDetailUiState.Success(
                        marketDetailUiData = coin.asUiData(
                            dollarToToman = dollarToToman,
                        ),
                        chart = previousState?.chart
                            ?: MarketChartUiState.Loading,
                        isRefreshing = previousState?.isRefreshing
                            ?: false,
                        isOffline = previousState?.isOffline
                            ?: false,
                    )
                }

                if (shouldLoadChart) {
                    loadCoinPriceHistory()
                }
            }
        }
    }

    private fun loadCoinPriceHistory() {
        if (navKey.assetType != MarketDetailAssetType.CRYPTO) {
            return
        }

        chartJob?.cancel()

        chartJob = viewModelScope.launch {
            setChartLoading()

            try {
                val points = getCoinPriceHistoryUseCase(
                    coinId = navKey.assetId,
                    days = 1,
                ).map(MarketPricePoint::asUiModel)

                setChartSuccess(points)
            } catch (exception: CancellationException) {
                throw exception
            } catch (_: Exception) {
                setChartError()
            }
        }
    }

    private fun setChartLoading() {
        _uiState.update { state ->
            state.mapSuccess { success ->
                success.copy(
                    chart = MarketChartUiState.Loading,
                )
            }
        }
    }

    private fun setChartSuccess(
        points: List<MarketPricePointUiModel>,
    ) {
        _uiState.update { state ->
            state.mapSuccess { success ->
                success.copy(
                    chart = MarketChartUiState.Success(
                        points = points,
                    ),
                )
            }
        }
    }

    private fun setChartError() {
        _uiState.update { state ->
            state.mapSuccess { success ->
                success.copy(
                    chart = MarketChartUiState.Error(
                        error = AppError.Unknown,
                    ),
                )
            }
        }
    }

    private fun refresh() {
        val state = _uiState.value.successOrNull()
            ?: return

        if (state.isRefreshing) {
            return
        }

        syncMarket()
    }

    private fun syncMarket() {
        viewModelScope.launch {
            setRefreshing(true)

            when (marketSyncer.sync()) {
                is AppResult.Success -> {
                    setRefreshing(false)
                    markOnline()

                    if (navKey.assetType == MarketDetailAssetType.CRYPTO) {
                        loadCoinPriceHistory()
                    }
                }

                is AppResult.Failure -> {
                    handleSyncFailure()
                }
            }
        }
    }

    private fun handleSyncFailure() {
        _uiState.update { state ->
            state.mapSuccess { success ->
                success.copy(
                    isRefreshing = false,
                    isOffline = true,
                )
            }
        }
    }

    private fun setRefreshing(isRefreshing: Boolean) {
        _uiState.update { state ->
            state.mapSuccess { success ->
                success.copy(
                    isRefreshing = isRefreshing,
                )
            }
        }
    }

    private fun markOnline() {
        _uiState.update { state ->
            state.mapSuccess { success ->
                success.copy(
                    isOffline = false,
                )
            }
        }
    }

    private fun setError(error: AppError) {
        _uiState.value = MarketDetailUiState.Error(
            error = error,
        )
    }

    fun onEvent(event: MarketDetailEvent) {
        when (event) {
            MarketDetailEvent.Refresh -> refresh()
            MarketDetailEvent.RetryChart -> loadCoinPriceHistory()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            navKey: MarketDetailNavKey,
        ): MarketDetailViewModel
    }

    private companion object {
        const val USD_ASSET_ID = "USD"
    }
}

private fun MarketDetailUiState.successOrNull():
        MarketDetailUiState.Success? =
    this as? MarketDetailUiState.Success

private inline fun MarketDetailUiState.mapSuccess(
    transform: (MarketDetailUiState.Success) -> MarketDetailUiState.Success,
): MarketDetailUiState {
    return when (this) {
        is MarketDetailUiState.Success -> transform(this)
        else -> this
    }
}