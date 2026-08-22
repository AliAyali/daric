package com.aliayali.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aliayali.database.dao.CoinDao
import com.aliayali.database.dao.MarketAssetDao
import com.aliayali.database.model.CoinEntity
import com.aliayali.database.model.MarketAssetEntity

@Database(
    entities = [
        CoinEntity::class,
        MarketAssetEntity::class
    ],
    version = 1,
    exportSchema = true,
)
internal abstract class DaricDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDao

    abstract fun marketAssetDao(): MarketAssetDao
}