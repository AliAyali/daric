package com.aliayali.marketdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aliayali.designsystem.component.DaricOfflineSnackbar
import com.aliayali.designsystem.component.DaricPullToRefresh
import com.aliayali.designsystem.component.ShimmerBox
import com.aliayali.marketdetail.components.MarketAssetHeader
import com.aliayali.marketdetail.components.chart.MarketChartCard
import com.aliayali.marketdetail.components.MarketDetailSkeleton
import com.aliayali.marketdetail.components.MarketDetailTopBar
import com.aliayali.marketdetail.components.MarketInfoCard
import com.aliayali.marketdetail.components.chart.MarketChartErrorContent
import com.aliayali.marketdetail.components.chart.MarketChartLoading
import com.aliayali.marketdetail.components.chart.MarketChartUnavailable
import com.aliayali.marketdetail.components.error.MarketDetailErrorContent
import com.aliayali.marketdetail.model.MarketDetailUiData

@Composable
fun MarketDetailScreen(
    uiState: MarketDetailUiState,
    retryChart: () -> Unit,
    onRefresh: () -> Unit,
    onBackClick: () -> Unit,
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    when (uiState) {

        MarketDetailUiState.Loading -> {
            MarketDetailSkeleton(
                modifier = Modifier.fillMaxSize(),
            )
        }

        is MarketDetailUiState.Success -> {
            val offlineTitle = stringResource(
                R.string.feature_marketdetail_offline_title,
            )

            val offlineMessage = stringResource(
                R.string.feature_marketdetail_offline_message,
            )

            LaunchedEffect(uiState.isOffline) {
                if (uiState.isOffline) {
                    snackbarHostState.showSnackbar(
                        message = buildString {
                            append(offlineTitle)
                            append("\n")
                            append(offlineMessage)
                        },
                        duration = SnackbarDuration.Indefinite,
                    )
                } else {
                    snackbarHostState.currentSnackbarData?.dismiss()
                }
            }
            val listState = rememberLazyListState()

            val isAtTop by remember {
                derivedStateOf {
                    listState.firstVisibleItemIndex == 0 &&
                            listState.firstVisibleItemScrollOffset == 0
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
            ) {
                DaricPullToRefresh(
                    isRefreshing = uiState.isRefreshing,
                    isAtTop = isAtTop,
                    onRefresh = onRefresh,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    MarketDetailContent(
                        data = uiState.marketDetailUiData,
                        chart = uiState.chart,
                        listState = listState,
                        onBackClick = onBackClick,
                        retryChart = retryChart,
                    )
                }

                DaricOfflineSnackbar(
                    hostState = snackbarHostState,
                    modifier = Modifier.align(
                        Alignment.BottomCenter,
                    ),
                )
            }
        }

        is MarketDetailUiState.Error -> {
            MarketDetailErrorContent(
                error = uiState.error,
                onRetry = onRefresh,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
private fun MarketDetailContent(
    data: MarketDetailUiData,
    chart: MarketChartUiState,
    listState: LazyListState,
    onBackClick: () -> Unit,
    retryChart: () -> Unit,
) {
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        item {
            MarketDetailTopBar(
                title = data.name,
                onBackClick = onBackClick,
            )
        }

        item {
            MarketAssetHeader(
                data = data,
            )
        }

        item {
            when (chart) {
                MarketChartUiState.Loading -> {
                    MarketChartLoading()
                }

                is MarketChartUiState.Success -> {
                    MarketChartCard(
                        points = chart.points,
                    )
                }

                MarketChartUiState.Unavailable -> {
                    MarketChartUnavailable()
                }

                is MarketChartUiState.Error -> {
                    MarketChartErrorContent(
                        onRetry = retryChart,
                    )
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