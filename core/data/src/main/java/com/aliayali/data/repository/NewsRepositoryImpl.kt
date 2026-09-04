package com.aliayali.data.repository

import com.aliayali.common.result.AppResult
import com.aliayali.data.error.asAppError
import com.aliayali.data.local.NewsLocalDataSource
import com.aliayali.domain.repository.NewsRepository
import com.aliayali.model.news.News
import com.aliayali.network.NewsNetworkDataSource
import com.aliayali.network.model.NewsArticleDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.cancellation.CancellationException

@Singleton
internal class NewsRepositoryImpl @Inject constructor(
    private val networkDataSource: NewsNetworkDataSource,
    private val localDataSource: NewsLocalDataSource,
) : NewsRepository {

    override fun observeNews(): Flow<List<News>> =
        localDataSource.observeNews()

    override suspend fun syncNews(
        query: String?,
        queryInTitle: String?,
        pageSize: Int,
        page: Int,
    ): AppResult<Unit> {
        return try {
            val news = networkDataSource
                .getNews(
                    query = query,
                    queryInTitle = queryInTitle,
                    pageSize = pageSize,
                    page = page,
                )
                .map(NewsArticleDto::asModel)

            localDataSource.saveNews(news)

            AppResult.Success(Unit)
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            AppResult.Failure(
                error = error.asAppError(),
            )
        }
    }
}

fun NewsArticleDto.asModel(): News =
    News(
        id = url,
        sourceId = source.id,
        sourceName = source.name,
        author = author,
        title = title,
        description = description,
        url = url,
        imageUrl = urlToImage,
        publishedAt = publishedAt,
        content = content,
    )