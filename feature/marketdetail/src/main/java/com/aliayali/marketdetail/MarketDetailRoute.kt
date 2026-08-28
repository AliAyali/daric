package com.aliayali.marketdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MarketDetailRoute(
    viewModel: MarketDetailViewModel,
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MarketDetailScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onRefresh = {
            viewModel.onEvent(MarketDetailEvent.Refresh)
        },
        retryChart = {
            viewModel.onEvent(MarketDetailEvent.RetryChart)
        },
    )
}