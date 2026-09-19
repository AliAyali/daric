package com.aliayali.market

import com.aliayali.common.error.AppError
import com.aliayali.market.model.MarketListState
import com.aliayali.market.model.MarketTab

sealed interface MarketUiState {

    data class Success(
        val selectedTab: MarketTab = MarketTab.CRYPTO,
        val cryptoState: MarketListState = MarketListState.Loading,
        val marketAssetState: MarketListState = MarketListState.Loading,
        val isRefreshing: Boolean = false,
        val isOffline: Boolean = false,
    ) : MarketUiState

    data class Error(
        val error: AppError,
    ) : MarketUiState
}