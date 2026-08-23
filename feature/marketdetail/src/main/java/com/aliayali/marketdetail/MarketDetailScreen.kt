package com.aliayali.marketdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MarketDetailScreen(
    uiState: MarketDetailUiState,
    onEvent: (MarketDetailEvent) -> Unit,
) {
    when (uiState) {

        MarketDetailUiState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Market Detail"
                )
            }
        }

        is MarketDetailUiState.Success -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Market Detail"
                )
            }
        }

        is MarketDetailUiState.Error -> {}
    }
}