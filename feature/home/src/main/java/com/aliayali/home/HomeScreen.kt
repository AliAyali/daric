package com.aliayali.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aliayali.designsystem.component.DaricLoadingIndicator
import com.aliayali.home.components.MarketOverviewCard

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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MarketOverviewCard()
            }
        }

        is HomeUiState.Error -> {}
    }
}