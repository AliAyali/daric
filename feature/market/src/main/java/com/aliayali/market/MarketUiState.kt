package com.aliayali.market

import com.aliayali.common.error.AppError
import com.aliayali.market.model.MarketItemUiModel
import com.aliayali.market.model.MarketTab

sealed interface MarketUiState {

    data object Loading : MarketUiState

    data class Success(
        val selectedTab: MarketTab = MarketTab.CRYPTO,
        val items: List<MarketItemUiModel> = emptyList(),
    ) : MarketUiState

    data class Error(
        val error: AppError,
    ) : MarketUiState
}