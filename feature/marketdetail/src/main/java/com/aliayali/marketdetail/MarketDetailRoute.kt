package com.aliayali.marketdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MarketDetailRoute(
    assetId: String,
    viewModel: MarketDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MarketDetailScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
    )
}