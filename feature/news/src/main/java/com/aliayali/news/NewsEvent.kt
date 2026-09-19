package com.aliayali.news

import com.aliayali.news.model.NewsCategory

sealed interface NewsEvent {
    data object Refresh : NewsEvent

    data class SelectedCategory(
        val category: NewsCategory,
    ) : NewsEvent
}