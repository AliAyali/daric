package com.aliayali.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aliayali.home.components.loading.MarketAssetItemSkeleton
import com.aliayali.home.components.loading.MarketOverviewCardSkeleton
import com.aliayali.home.components.market.MarketAssetItem
import com.aliayali.home.components.market.MarketListHeader
import com.aliayali.home.components.overview.MarketOverviewCard
import com.aliayali.home.model.CoinUiModel
import com.aliayali.home.model.MarketOverviewCardUiModel

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit,
) {
    when (uiState) {

        HomeUiState.Loading -> {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                MarketOverviewCardSkeleton()
                Spacer(Modifier.height(20.dp))
                repeat(2) {
                    MarketAssetItemSkeleton()
                }
            }
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
                    coins = uiState.coins,
                    onMoreClick = {
                        onEvent(HomeEvent.SectionMoreClick)
                    },
                    onCoinClick = {
                        onEvent(HomeEvent.CoinClick(it))
                    }
                )
            }
        }

        is HomeUiState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                androidx.compose.material3.Text(
                    text = "Error: ${uiState.message}",
                )
            }
        }
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
    coins: List<CoinUiModel>,
    onMoreClick: () -> Unit,
    onCoinClick: (String) -> Unit,
) {
    item {
        MarketListHeader(
            onMoreClick = onMoreClick
        )
    }
    items(coins) { coin ->
        MarketAssetItem(
            item = coin,
            onCoinClick = onCoinClick
        )
    }
}