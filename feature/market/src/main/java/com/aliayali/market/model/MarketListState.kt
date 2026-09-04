package com.aliayali.market.model

import com.aliayali.common.error.AppError

sealed interface MarketListState {

    data object Loading : MarketListState

    data class Success(
        val items: List<MarketItemUiModel>,
    ) : MarketListState

    data class Error(
        val error: AppError,
    ) : MarketListState
}