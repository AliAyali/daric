package com.aliayali.data.local

import com.aliayali.model.news.News
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {

    fun observeNews(
        category: String,
    ): Flow<List<News>>

    suspend fun saveNews(
        news: List<News>,
    )
}