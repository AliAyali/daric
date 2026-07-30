package com.aliayali.home

import com.aliayali.home.model.CoinUiModel

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Success(
        val featuredItems: List<CoinUiModel>,
        val popularItems: List<CoinUiModel>,
    ) : HomeUiState

    data class Error(
        val message: String,
    ) : HomeUiState
}