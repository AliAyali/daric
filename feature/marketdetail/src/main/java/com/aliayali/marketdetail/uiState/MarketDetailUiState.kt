package com.aliayali.marketdetail.uiState

import com.aliayali.common.error.AppError
import com.aliayali.marketdetail.model.MarketDetailUiData

sealed interface MarketDetailUiState {

    data object Loading : MarketDetailUiState

    data class Success(
        val marketDetailUiData: MarketDetailUiData,
        val chart: MarketChartUiState,
    ) : MarketDetailUiState

    data class Error(
        val error: AppError,
    ) : MarketDetailUiState
}