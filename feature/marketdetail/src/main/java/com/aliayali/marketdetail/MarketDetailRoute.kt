package com.aliayali.marketdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MarketDetailRoute(
    viewModel: MarketDetailViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MarketDetailScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
    )
}