package com.aliayali.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.aliayali.designsystem.component.DaricLoadingIndicator

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

        }

        is HomeUiState.Error -> {

        }
    }
}