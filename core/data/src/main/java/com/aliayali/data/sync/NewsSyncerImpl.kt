package com.aliayali.data.sync

import com.aliayali.common.result.AppResult
import com.aliayali.domain.repository.NewsRepository
import com.aliayali.domain.sync.NewsSyncer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class NewsSyncerImpl @Inject constructor(
    private val newsRepository: NewsRepository,
) : NewsSyncer {

    override suspend fun sync(
        category: String,
        query: String,
    ): AppResult<Unit> {
        return newsRepository.syncNews(
            category = category,
            query = query,
            pageSize = 20,
        )
    }
}