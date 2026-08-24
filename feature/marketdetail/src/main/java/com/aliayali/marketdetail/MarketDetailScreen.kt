package com.aliayali.marketdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aliayali.marketdetail.components.MarketAssetHeader
import com.aliayali.marketdetail.components.MarketChartCard
import com.aliayali.marketdetail.components.MarketDetailTopBar
import com.aliayali.marketdetail.components.MarketInfoCard
import com.aliayali.marketdetail.components.MarketPriceSection
import com.aliayali.marketdetail.model.MarketDetailUiData
import com.aliayali.marketdetail.uiState.MarketChartUiState
import com.aliayali.marketdetail.uiState.MarketDetailUiState

@Composable
fun MarketDetailScreen(
    uiState: MarketDetailUiState,
    onEvent: (MarketDetailEvent) -> Unit,
) {
    when (uiState) {
        MarketDetailUiState.Loading -> {

        }

        is MarketDetailUiState.Success -> {
            MarketDetailContent(
                data = uiState.marketDetailUiData,
                chart = uiState.chart,
                onEvent = onEvent,
            )
        }

        is MarketDetailUiState.Error -> {

        }
    }
}

@Composable
private fun MarketDetailContent(
    data: MarketDetailUiData,
    chart: MarketChartUiState,
    onEvent: (MarketDetailEvent) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        item {
            MarketDetailTopBar(
                title = data.name,
                onBackClick = {

                },
            )
        }

        item {
            MarketAssetHeader(
                data = data,
            )
        }

        item {
            MarketPriceSection(
                data = data,
            )
        }

        item {
            when (chart) {
                MarketChartUiState.Loading -> {

                }

                is MarketChartUiState.Success -> {
                    MarketChartCard(
                        points = chart.points,
                    )
                }

                MarketChartUiState.Unavailable -> {

                }

                is MarketChartUiState.Error -> {

                }
            }
        }

        item {
            MarketInfoCard(
                data = data,
            )
        }
    }
}