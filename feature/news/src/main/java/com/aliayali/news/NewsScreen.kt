package com.aliayali.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.aliayali.news.components.NewsCategoryChip
import com.aliayali.news.components.NewsErrorContent
import com.aliayali.news.components.NewsHeader
import com.aliayali.news.components.NewsItem
import com.aliayali.news.components.NewsItemShimmer
import com.aliayali.news.model.NewsCategory

@Composable
fun NewsScreen(
    uiState: NewsUiState,
    onEvent: (NewsEvent) -> Unit,
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    when (uiState) {

        NewsUiState.Loading -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item {
                    NewsHeader()
                }

                items(6) {
                    NewsItemShimmer()
                }
            }
        }

        is NewsUiState.Success -> {

            val offlineTitle = stringResource(
                R.string.feature_news_offline_title,
            )

            val offlineMessage = stringResource(
                R.string.feature_news_offline_message,
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
                modifier = Modifier.fillMaxSize(),
            ) {
                DaricPullToRefresh(
                    isRefreshing = uiState.isRefreshing,
                    isAtTop = isAtTop,
                    onRefresh = {
                        onEvent(NewsEvent.Refresh)
                    },
                    modifier = Modifier.fillMaxSize(),
                ) {

                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {

                        item {
                            NewsHeader()
                        }

                        item {
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(
                                    start = 16.dp,
                                    end = 16.dp,
                                ),
                                horizontalArrangement = Arrangement.End,
                                reverseLayout = true,
                            ) {
                                items(NewsCategory.entries) { category ->
                                    NewsCategoryChip(
                                        category = category,
                                        selected =
                                            category == uiState.selectedCategory,
                                        onClick = {
                                            onEvent(
                                                NewsEvent.SelectedCategory(
                                                    category = category,
                                                )
                                            )
                                        },
                                    )
                                }
                            }
                        }

                        items(
                            items = uiState.news,
                            key = { it.id },
                        ) { news ->
                            NewsItem(news)
                        }
                    }
                }

                DaricOfflineSnackbar(
                    hostState = snackbarHostState,
                    modifier = Modifier.align(
                        Alignment.BottomCenter,
                    ),
                )
            }
        }

        is NewsUiState.Error -> {
            NewsErrorContent(
                error = uiState.error,
                onRetry = {
                    onEvent(NewsEvent.Refresh)
                },
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}