package com.aliayali.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.aliayali.database.model.CoinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Upsert
    suspend fun upsertAll(coins: List<CoinEntity>)

    @Query("SELECT * FROM coins")
    fun observeAll(): Flow<List<CoinEntity>>
}