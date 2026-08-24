package com.aliayali.marketdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Loading...",
                )
            }
        }

        is MarketDetailUiState.Success -> {
            val data = uiState.marketDetailUiData

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = data.name,
                )

                Text(
                    text = data.symbol,
                )

                Text(
                    text = data.price?.toString() ?: "-",
                )

                Text(
                    text = data.changePercent?.toString() ?: "-",
                )

                Text(
                    text = data.unit ?: "-",
                )
            }
        }

        is MarketDetailUiState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Error",
                )
            }
        }
    }
}