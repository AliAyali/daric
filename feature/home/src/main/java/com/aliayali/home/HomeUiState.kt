package com.aliayali.home

import com.aliayali.home.model.MarketOverviewCardUiModel
import com.aliayali.home.model.MarketSectionCardUiModel

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Success(
        val overview: MarketOverviewCardUiModel,
        val sections: List<MarketSectionCardUiModel>,
    ) : HomeUiState

    data class Error(
        val message: String,
    ) : HomeUiState
}