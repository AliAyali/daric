package com.aliayali.home

import com.aliayali.home.model.CoinUiModel
import com.aliayali.home.model.MarketOverviewCardUiModel

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Success(
        val overview: MarketOverviewCardUiModel,
        val coins: List<CoinUiModel>,
    ) : HomeUiState

    data class Error(
        val message: String,
    ) : HomeUiState
}