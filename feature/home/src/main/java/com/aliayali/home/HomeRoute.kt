package com.aliayali.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeRoute(
    onCoinClick: (String) -> Unit,
    onSectionMoreClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onRefresh = {
            viewModel.onEvent(HomeEvent.Refresh)
        },
        onCoinClick = onCoinClick,
        onSectionMoreClick = onSectionMoreClick,
    )
}