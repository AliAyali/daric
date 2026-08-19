package com.aliayali.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.aliayali.database.model.MarketAssetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarketAssetDao {
    @Upsert
    suspend fun upsertAll(assets: List<MarketAssetEntity>)

    @Query("SELECT * FROM market_assets")
    fun observeAll(): Flow<List<MarketAssetEntity>>
}