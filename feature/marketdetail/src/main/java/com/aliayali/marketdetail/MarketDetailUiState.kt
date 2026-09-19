package com.aliayali.marketdetail

import com.aliayali.common.error.AppError
import com.aliayali.marketdetail.model.MarketDetailUiData
import com.aliayali.marketdetail.model.MarketPricePointUiModel

sealed interface MarketDetailUiState {

    data object Loading : MarketDetailUiState

    data class Success(
        val marketDetailUiData: MarketDetailUiData,
        val chart: MarketChartUiState,
        val isRefreshing: Boolean = false,
        val isOffline: Boolean = false,
    ) : MarketDetailUiState

    data class Error(
        val error: AppError,
    ) : MarketDetailUiState
}

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