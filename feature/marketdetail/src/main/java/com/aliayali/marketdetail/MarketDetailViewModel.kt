package com.aliayali.marketdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.common.error.AppError
import com.aliayali.domain.GetCoinDetailUseCase
import com.aliayali.domain.GetMarketAssetDetailUseCase
import com.aliayali.marketdetail.mapper.asUiData
import com.aliayali.marketdetail.navigation.MarketDetailAssetType
import com.aliayali.marketdetail.navigation.MarketDetailNavKey
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = MarketDetailViewModel.Factory::class)
class MarketDetailViewModel @AssistedInject constructor(
    @Assisted val navKey: MarketDetailNavKey,
    private val getMarketAssetDetailUseCase: GetMarketAssetDetailUseCase,
    private val getCoinDetailUseCase: GetCoinDetailUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MarketDetailUiState>(
        MarketDetailUiState.Loading,
    )

    val uiState: StateFlow<MarketDetailUiState> =
        _uiState.asStateFlow()

    init {
        observeDetail()
    }

    private fun observeDetail() {
        viewModelScope.launch {
            when (navKey.assetType) {

                MarketDetailAssetType.MARKET -> {
                    getMarketAssetDetailUseCase(
                        id = navKey.assetId,
                    ).collect { asset ->

                        if (asset == null) {
                            _uiState.value = MarketDetailUiState.Error(
                                error = AppError.Unknown,
                            )
                            return@collect
                        }

                        _uiState.value =
                            MarketDetailUiState.Success(
                                marketDetailUiData = asset.asUiData(),
                            )
                    }
                }

                MarketDetailAssetType.CRYPTO -> {
                    getCoinDetailUseCase(
                        id = navKey.assetId,
                    ).collect { coin ->

                        if (coin == null) {
                            _uiState.value = MarketDetailUiState.Error(
                                error = AppError.Unknown,
                            )
                            return@collect
                        }

                        _uiState.value =
                            MarketDetailUiState.Success(
                                marketDetailUiData = coin.asUiData(),
                            )
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