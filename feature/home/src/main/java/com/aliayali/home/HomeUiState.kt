package com.aliayali.home

import com.aliayali.home.model.CoinUiModel
import com.aliayali.home.model.MarketAssetUiModel
import com.aliayali.home.model.MarketOverviewCardUiModel
import com.aliayali.model.error.AppError

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Success(
        val overview: MarketOverviewCardUiModel,
        val coins: List<CoinUiModel>,
        val marketAssets: List<MarketAssetUiModel>,
        val isRefreshing: Boolean = false,
        val isOffline: Boolean = false,
    ) : HomeUiState

    data class Error(
        val error: AppError,
    ) : HomeUiState
}