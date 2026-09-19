package com.aliayali.domain

import com.aliayali.domain.repository.NewsRepository
import com.aliayali.model.news.News
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
) {
    operator fun invoke(
        category: String,
    ): Flow<List<News>> {
        return newsRepository.observeNews(
            category = category,
        )
    }
}