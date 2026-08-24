package com.aliayali.marketdetail.uiState

import com.aliayali.common.error.AppError
import com.aliayali.marketdetail.model.MarketPricePointUiModel

sealed interface MarketChartUiState {

    data object Loading : MarketChartUiState

    data class Success(
        val points: List<MarketPricePointUiModel>,
    ) : MarketChartUiState

    data object Unavailable : MarketChartUiState

    data class Error(
        val error: AppError,
    ) : MarketChartUiState
}