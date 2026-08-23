package com.aliayali.marketdetail

import com.aliayali.common.error.AppError
import com.aliayali.marketdetail.model.MarketDetailUiData

sealed interface MarketDetailUiState {
    data object Loading : MarketDetailUiState

    data class Success(
        val marketDetailUiData: MarketDetailUiData,
    ) : MarketDetailUiState

    data class Error(
        val error: AppError,
    ) : MarketDetailUiState
}