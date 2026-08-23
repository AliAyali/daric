package com.aliayali.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aliayali.designsystem.component.DaricOfflineSnackbar
import com.aliayali.designsystem.component.DaricPullToRefresh
import com.aliayali.home.components.error.HomeErrorContent
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
    onRefresh: () -> Unit,
    onCoinClick: (String) -> Unit,
    onSectionMoreClick: () -> Unit,
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    when (uiState) {

        HomeUiState.Loading -> {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                MarketOverviewCardSkeleton()

                Spacer(
                    modifier = Modifier.height(20.dp),
                )

                repeat(2) {
                    MarketAssetItemSkeleton()
                }
            }
        }

        is HomeUiState.Success -> {
            val offlineTitle = stringResource(
                R.string.feature_home_offline_title,
            )

            val offlineMessage = stringResource(
                R.string.feature_home_offline_message,
            )
            LaunchedEffect(uiState.isOffline) {
                if (uiState.isOffline) {
                    snackbarHostState.showSnackbar(
                        message = buildString {
                            append(offlineTitle)
                            append("\n")
                            append(offlineMessage)
                        },
                        duration = SnackbarDuration.Indefinite,
                    )
                } else {
                    snackbarHostState.currentSnackbarData?.dismiss()
                }
            }

            val listState = rememberLazyListState()

            val isAtTop by remember {
                derivedStateOf {
                    listState.firstVisibleItemIndex == 0 &&
                            listState.firstVisibleItemScrollOffset == 0
                }
            }

            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                DaricPullToRefresh(
                    isRefreshing = uiState.isRefreshing,
                    isAtTop = isAtTop,
                    onRefresh = {
                        onRefresh()
                    },
                    modifier = Modifier.fillMaxSize(),
                ) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                    ) {
                        homeOverview(uiState.homeUiData.overview)

                        homeSections(
                            coins = uiState.homeUiData.coins,
                            onMoreClick = onSectionMoreClick,
                            onCoinClick = onCoinClick,
                        )
                    }
                }

                DaricOfflineSnackbar(
                    hostState = snackbarHostState,
                    modifier = Modifier.align(
                        Alignment.BottomCenter,
                    ),
                )
            }
        }

        is HomeUiState.Error -> {
            HomeErrorContent(
                error = uiState.error,
                onRetry = {
                    onRefresh()
                },
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

private fun LazyListScope.homeOverview(
    overview: MarketOverviewCardUiModel,
) {
    item {
        MarketOverviewCard(
            overview = overview,
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
            onMoreClick = onMoreClick,
        )
    }

    items(coins) { coin ->
        MarketAssetItem(
            item = coin,
            onCoinClick = onCoinClick,
        )
    }
}