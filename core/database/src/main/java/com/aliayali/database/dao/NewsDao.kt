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
        ORDER BY publishedAt DESC
        """
    )
    fun observeAll(): Flow<List<NewsEntity>>

    @Upsert
    suspend fun upsertAll(news: List<NewsEntity>)

    @Query(
        """
        DELETE FROM news
        WHERE id NOT IN (
            SELECT id
            FROM news
            ORDER BY publishedAt DESC
            LIMIT 50
        )
        """
    )
    suspend fun deleteOldNews()

    @Transaction
    suspend fun upsertAndDeleteOld(news: List<NewsEntity>) {
        upsertAll(news)
        deleteOldNews()
    }
}