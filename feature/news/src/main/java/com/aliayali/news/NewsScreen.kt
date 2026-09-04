package com.aliayali.news

import androidx.compose.runtime.Composable
import com.aliayali.news.components.NewsHeader

@Composable
fun NewsScreen(
    uiState: NewsUiState,
    onRefresh: () -> Unit,
) {
    when (uiState) {
        NewsUiState.Loading -> {

        }

        NewsUiState.Success -> {
            NewsHeader()
        }

        is NewsUiState.Error -> {}
    }
}