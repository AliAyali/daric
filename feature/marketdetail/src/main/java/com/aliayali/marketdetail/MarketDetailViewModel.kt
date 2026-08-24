package com.aliayali.marketdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.common.error.AppError
import com.aliayali.domain.GetCoinDetailUseCase
import com.aliayali.domain.GetCoinPriceHistoryUseCase
import com.aliayali.domain.GetMarketAssetDetailUseCase
import com.aliayali.marketdetail.mapper.asUiData
import com.aliayali.marketdetail.mapper.asUiModel
import com.aliayali.marketdetail.navigation.MarketDetailAssetType
import com.aliayali.marketdetail.navigation.MarketDetailNavKey
import com.aliayali.marketdetail.uiState.MarketChartUiState
import com.aliayali.marketdetail.uiState.MarketDetailUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
) : ViewModel() {

    private val _uiState = MutableStateFlow<MarketDetailUiState>(
        MarketDetailUiState.Loading,
    )

    val uiState: StateFlow<MarketDetailUiState> =
        _uiState.asStateFlow()

    init {
        observeDetail()

        if (navKey.assetType == MarketDetailAssetType.CRYPTO) {
            loadCoinPriceHistory()
        }
    }

    private fun observeDetail() {
        viewModelScope.launch {
            when (navKey.assetType) {

                MarketDetailAssetType.MARKET -> {
                    observeMarketAsset()
                }

                MarketDetailAssetType.CRYPTO -> {
                    observeCoin()
                }
            }
        }
    }

    private suspend fun observeMarketAsset() {
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
            )
        }
    }

    private suspend fun observeCoin() {
        getCoinDetailUseCase(
            id = navKey.assetId,
        ).collect { coin ->

            if (coin == null) {
                _uiState.value = MarketDetailUiState.Error(
                    error = AppError.Unknown,
                )
                return@collect
            }

            val currentState = _uiState.value

            _uiState.value = MarketDetailUiState.Success(
                marketDetailUiData = coin.asUiData(),
                chart = when (currentState) {
                    is MarketDetailUiState.Success -> {
                        currentState.chart
                    }

                    else -> {
                        MarketChartUiState.Loading
                    }
                },
            )
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
            } catch (error: Exception) {
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

    @AssistedFactory
    interface Factory {
        fun create(
            navKey: MarketDetailNavKey,
        ): MarketDetailViewModel
    }

    fun onEvent(event: MarketDetailEvent) {
        when (event) {
            MarketDetailEvent.Refresh -> Unit
        }
    }
}