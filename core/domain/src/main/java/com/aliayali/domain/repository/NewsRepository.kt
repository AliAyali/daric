package com.aliayali.domain.repository

import com.aliayali.common.result.AppResult
import com.aliayali.model.news.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun observeNews(): Flow<List<News>>

    suspend fun syncNews(
        query: String? = null,
        queryInTitle: String? = null,
        pageSize: Int = 20,
        page: Int = 1,
    ): AppResult<Unit>
}