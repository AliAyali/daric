package com.aliayali.market

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MarketRoute(
    onCoinClick: (String) -> Unit,
    onMarketAssetClick: (String) -> Unit,
    viewModel: MarketViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MarketScreen(
        uiState = uiState,
        onCoinClick = onCoinClick,
        onMarketAssetClick = onMarketAssetClick,
        onRefresh = {
            viewModel.onEvent(MarketEvent.Refresh)
        },
        onTabSelected = { tab ->
            viewModel.onEvent(
                MarketEvent.SelectTab(tab),
            )
        },
    )
}