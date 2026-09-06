package com.aliayali.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.aliayali.database.model.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query(
        """
        SELECT * FROM news
        WHERE category = :category
        ORDER BY publishedAt DESC
        """
    )
    fun observeByCategory(
        category: String,
    ): Flow<List<NewsEntity>>

    @Upsert
    suspend fun upsertAll(
        news: List<NewsEntity>,
    )

    @Query(
        """
        DELETE FROM news
        WHERE category = :category
        AND id NOT IN (
            SELECT id
            FROM news
            WHERE category = :category
            ORDER BY publishedAt DESC
            LIMIT 50
        )
        """
    )
    suspend fun deleteOldNews(
        category: String,
    )

    @Transaction
    suspend fun upsertAndDeleteOld(
        news: List<NewsEntity>,
    ) {
        upsertAll(news)

        news
            .map { it.category }
            .distinct()
            .forEach { category ->
                deleteOldNews(category)
            }
    }
}