package com.aliayali.network

import com.aliayali.network.error.safeNetworkCall
import com.aliayali.network.model.NewsArticleDto
import com.aliayali.network.retrofit.NewsApi
import javax.inject.Inject
import javax.inject.Singleton

interface NewsNetworkDataSource {

    suspend fun getNews(
        query: String? = null,
        queryInTitle: String? = null,
        pageSize: Int = 20,
        page: Int = 1,
    ): List<NewsArticleDto>
}

@Singleton
class RetrofitNewsNetworkDataSource @Inject constructor(
    private val api: NewsApi,
) : NewsNetworkDataSource {

    override suspend fun getNews(
        query: String?,
        queryInTitle: String?,
        pageSize: Int,
        page: Int,
    ): List<NewsArticleDto> =
        safeNetworkCall {
            api.getEverything(
                query = query,
                queryInTitle = queryInTitle,
                language = "fa",
                sortBy = "publishedAt",
                pageSize = pageSize,
                page = page,
            )
        }.articles
}