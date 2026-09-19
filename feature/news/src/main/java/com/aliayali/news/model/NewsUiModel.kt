package com.aliayali.news.model

data class NewsUiModel(
    val id: String,
    val sourceName: String,
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val publishedAt: String,
    val url: String,
)