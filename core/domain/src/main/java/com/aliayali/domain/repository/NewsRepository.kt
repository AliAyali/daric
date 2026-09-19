package com.aliayali.domain.repository

import com.aliayali.common.result.AppResult
import com.aliayali.model.news.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun observeNews(
        category: String,
    ): Flow<List<News>>

    suspend fun syncNews(
        category: String,
        query: String,
        queryInTitle: String? = null,
        pageSize: Int = 20,
        page: Int = 1,
    ): AppResult<Unit>
}