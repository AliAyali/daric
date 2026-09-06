package com.aliayali.data.local

import android.util.Log
import com.aliayali.database.dao.NewsDao
import com.aliayali.database.model.NewsEntity
import com.aliayali.database.model.asEntity
import com.aliayali.database.model.asModel
import com.aliayali.model.news.News
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class NewsLocalDataSourceImpl @Inject constructor(
    private val newsDao: NewsDao,
) : NewsLocalDataSource {

    override fun observeNews(
        category: String,
    ): Flow<List<News>> =
        newsDao
            .observeByCategory(category)
            .map { entities ->
                entities.map(NewsEntity::asModel)
            }

    override suspend fun saveNews(
        news: List<News>,
    ) {
        newsDao.upsertAndDeleteOld(
            news.map(News::asEntity),
        )
    }
}