package com.aliayali.news

import com.aliayali.common.error.AppError
import com.aliayali.news.model.NewsCategory
import com.aliayali.news.model.NewsUiModel

sealed interface NewsUiState {
    data object Loading : NewsUiState

    data class Success(
        val news: List<NewsUiModel>,
        val isRefreshing: Boolean = false,
        val isOffline: Boolean = false,
        val selectedCategory: NewsCategory = NewsCategory.ALL,
    ) : NewsUiState

    data class Error(
        val error: AppError,
    ) : NewsUiState
}