package com.aliayali.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aliayali.designsystem.component.DaricLoadingIndicator
import com.aliayali.home.components.MarketOverviewCard
import com.aliayali.home.components.MarketSection

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

                item {
                    MarketOverviewCard(
                        overview = uiState.overview
                    )
                }

                items(uiState.sections) { section ->

                    MarketSection(
                        section = section,
                        onMoreClick = {
                            onEvent(HomeEvent.SectionMoreClick)
                        },
                        onCoinClick = {
                            onEvent(HomeEvent.CoinClick("0"))
                        }
                    )

                }

            }
        }

        is HomeUiState.Error -> {}
    }
}