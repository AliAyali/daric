package com.aliayali.network.model

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class NewsResponseDto(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsArticleDto>,
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class NewsArticleDto(
    val source: NewsSourceDto,
    val author: String? = null,
    val title: String,
    val description: String? = null,
    val url: String,
    val urlToImage: String? = null,
    val publishedAt: String,
    val content: String? = null,
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class NewsSourceDto(
    val id: String? = null,
    val name: String,
)