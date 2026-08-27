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
import com.aliayali.marketdetail.navigation.MarketDetailAssetType
import com.aliayali.marketdetail.navigation.MarketDetailNavKey
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
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
    @Assisted val navKey: MarketDetailNavKey,
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

    private var isOnline: Boolean? = null

    init {
        observeNetwork()
        observeDetail()
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            observeNetworkConnectivityUseCase()
                .collect { online ->

                    val wasOffline = isOnline == false
                    isOnline = online

                    _uiState.update { currentState ->
                        if (currentState is MarketDetailUiState.Success) {
                            currentState.copy(
                                isOffline = !online,
                            )
                        } else {
                            currentState
                        }
                    }

                    if (wasOffline && online) {
                        refresh()
                    }
                }
        }
    }

    private fun observeDetail() {
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
                    _uiState.value = MarketDetailUiState.Error(
                        error = AppError.Unknown,
                    )
                    return@collect
                }

                _uiState.value = MarketDetailUiState.Success(
                    marketDetailUiData = asset.asUiData(),
                    chart = MarketChartUiState.Unavailable,
                    isRefreshing = false,
                    isOffline = isOnline != true,
                )
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
                    _uiState.value = MarketDetailUiState.Error(
                        error = AppError.Unknown,
                    )
                    return@collect
                }

                val currentState = _uiState.value

                val chartState = when (currentState) {
                    is MarketDetailUiState.Success -> {
                        currentState.chart
                    }

                    else -> {
                        MarketChartUiState.Loading
                    }
                }

                _uiState.value = MarketDetailUiState.Success(
                    marketDetailUiData = coin.asUiData(
                        dollarToToman = dollarToToman,
                    ),
                    chart = chartState,
                    isRefreshing = currentState is MarketDetailUiState.Success &&
                            currentState.isRefreshing,
                    isOffline = isOnline != true,
                )

                if (
                    currentState !is MarketDetailUiState.Success ||
                    chartState is MarketChartUiState.Error
                ) {
                    loadCoinPriceHistory()
                }
            }
        }
    }

    private fun loadCoinPriceHistory() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                if (currentState is MarketDetailUiState.Success) {
                    currentState.copy(
                        chart = MarketChartUiState.Loading,
                    )
                } else {
                    currentState
                }
            }

            try {
                val points = getCoinPriceHistoryUseCase(
                    coinId = navKey.assetId,
                    days = 1,
                ).map { it.asUiModel() }

                _uiState.update { currentState ->
                    if (currentState is MarketDetailUiState.Success) {
                        currentState.copy(
                            chart = MarketChartUiState.Success(
                                points = points,
                            ),
                        )
                    } else {
                        currentState
                    }
                }
            } catch (error: CancellationException) {
                throw error
            } catch (_: Exception) {
                _uiState.update { currentState ->
                    if (currentState is MarketDetailUiState.Success) {
                        currentState.copy(
                            chart = MarketChartUiState.Error(
                                error = AppError.Unknown,
                            ),
                        )
                    } else {
                        currentState
                    }
                }
            }
        }
    }

    private fun performSync() {
        viewModelScope.launch {

            _uiState.update { currentState ->
                if (currentState is MarketDetailUiState.Success) {
                    currentState.copy(
                        isRefreshing = true,
                    )
                } else {
                    currentState
                }
            }

            when (val result = marketSyncer.sync()) {

                is AppResult.Success -> {
                    if (navKey.assetType == MarketDetailAssetType.CRYPTO) {
                        loadCoinPriceHistory()
                    }

                    _uiState.update { currentState ->
                        if (currentState is MarketDetailUiState.Success) {
                            currentState.copy(
                                isRefreshing = false,
                                isOffline = false,
                            )
                        } else {
                            currentState
                        }
                    }
                }

                is AppResult.Failure -> {
                    _uiState.update { currentState ->
                        if (currentState is MarketDetailUiState.Success) {
                            currentState.copy(
                                isRefreshing = false,
                                isOffline = true,
                            )
                        } else {
                            MarketDetailUiState.Error(
                                error = result.error,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun refresh() {
        val currentState = _uiState.value

        if (currentState !is MarketDetailUiState.Success) {
            return
        }

        if (currentState.isRefreshing) {
            return
        }

        performSync()
    }

    @AssistedFactory
    interface Factory {
        fun create(
            navKey: MarketDetailNavKey,
        ): MarketDetailViewModel
    }

    fun onEvent(event: MarketDetailEvent) {
        when (event) {
            MarketDetailEvent.Refresh -> refresh()
            MarketDetailEvent.RetryChart -> loadCoinPriceHistory()
        }
    }

    private companion object {
        const val USD_ASSET_ID = "USD"
    }
}