package com.aliayali.market

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.aliayali.market.components.MarketItem
import com.aliayali.market.components.MarketTabs
import com.aliayali.market.model.MarketItemUiModel
import com.aliayali.market.model.MarketListState
import com.aliayali.market.model.MarketTab

@Composable
fun MarketScreen(
    uiState: MarketUiState,
    onCoinClick: (String) -> Unit,
    onMarketAssetClick: (String) -> Unit,
    onRefresh: () -> Unit,
    onTabSelected: (MarketTab) -> Unit,
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    when (uiState) {

        is MarketUiState.Success -> {

            val offlineTitle = stringResource(
                R.string.feature_market_offline_title,
            )

            val offlineMessage = stringResource(
                R.string.feature_market_offline_message,
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

            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    MarketTabs(
                        selectedTab = uiState.selectedTab,
                        onTabSelected = onTabSelected,
                    )

                    val listState = when (uiState.selectedTab) {
                        MarketTab.CRYPTO -> uiState.cryptoState
                        MarketTab.MARKET_ASSET -> uiState.marketAssetState
                    }

                    MarketContent(
                        listState = listState,
                        isRefreshing = uiState.isRefreshing,
                        onRefresh = onRefresh,
                        onCoinClick = onCoinClick,
                        onMarketAssetClick = onMarketAssetClick,
                        modifier = Modifier.weight(1f),
                    )
                }

                DaricOfflineSnackbar(
                    hostState = snackbarHostState,
                    modifier = Modifier.align(
                        Alignment.BottomCenter,
                    ),
                )
            }
        }

        is MarketUiState.Error -> {
            // Global Error UI
        }
    }
}

@Composable
private fun MarketContent(
    listState: MarketListState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onCoinClick: (String) -> Unit,
    onMarketAssetClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (listState) {

        MarketListState.Loading -> {
            // Loading UI
        }

        is MarketListState.Success -> {
            MarketList(
                items = listState.items,
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
                onCoinClick = onCoinClick,
                onMarketAssetClick = onMarketAssetClick,
                modifier = modifier,
            )
        }

        is MarketListState.Error -> {
            // Error UI
        }
    }
}

@Composable
private fun MarketList(
    items: List<MarketItemUiModel>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onCoinClick: (String) -> Unit,
    onMarketAssetClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()

    val isAtTop by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0 &&
                    listState.firstVisibleItemScrollOffset == 0
        }
    }

    DaricPullToRefresh(
        isRefreshing = isRefreshing,
        isAtTop = isAtTop,
        onRefresh = onRefresh,
        modifier = modifier
            .fillMaxSize()
            .padding(
                vertical = 8.dp
            ),
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            items(
                items = items,
                key = { item ->
                    when (item) {
                        is MarketItemUiModel.Coin ->
                            "coin_${item.id}"

                        is MarketItemUiModel.MarketAsset ->
                            "asset_${item.id}"
                    }
                },
            ) { item ->
                MarketItem(
                    item = item,
                    onCoinClick = onCoinClick,
                    onMarketAssetClick = onMarketAssetClick,
                )
            }
        }
    }
}