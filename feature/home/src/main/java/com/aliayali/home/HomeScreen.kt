package com.aliayali.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aliayali.designsystem.component.DaricLoadingIndicator
import com.aliayali.home.components.overview.MarketOverviewCard
import com.aliayali.home.components.market.MarketSection
import com.aliayali.home.model.MarketOverviewCardUiModel
import com.aliayali.home.model.MarketSectionCardUiModel

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit,
) {
    when (uiState) {

        HomeUiState.Loading -> {
            DaricLoadingIndicator()
        }

        is HomeUiState.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {

                homeOverview(uiState.overview)

                homeSections(
                    sections = uiState.sections,
                    onMoreClick = {
                        onEvent(HomeEvent.SectionMoreClick)
                    },
                    onCoinClick = {
                        onEvent(HomeEvent.CoinClick(it))
                    }
                )
            }

        }

        is HomeUiState.Error -> {}
    }
}

private fun LazyListScope.homeOverview(
    overview: MarketOverviewCardUiModel,
) {
    item {
        MarketOverviewCard(
            overview = overview
        )
    }
}

private fun LazyListScope.homeSections(
    sections: List<MarketSectionCardUiModel>,
    onMoreClick: () -> Unit,
    onCoinClick: (String) -> Unit,
) {
    items(sections) { section ->
        MarketSection(
            section = section,
            onMoreClick = onMoreClick,
            onCoinClick = onCoinClick,
        )
    }
}