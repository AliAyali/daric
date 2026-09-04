package com.aliayali.news

import com.aliayali.common.error.AppError

sealed interface NewsUiState {
    data object Loading : NewsUiState

    data object Success : NewsUiState

    data class Error(
        val error: AppError,
    ) : NewsUiState
}