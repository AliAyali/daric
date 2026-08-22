package com.aliayali.home

import com.aliayali.common.error.AppError
import com.aliayali.home.model.HomeUiData

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Success(
        val homeUiData: HomeUiData,
        val isRefreshing: Boolean = false,
        val isOffline: Boolean = false,
    ) : HomeUiState

    data class Error(
        val error: AppError,
    ) : HomeUiState
}