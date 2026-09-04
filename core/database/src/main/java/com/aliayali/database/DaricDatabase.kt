package com.aliayali.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aliayali.database.dao.CoinDao
import com.aliayali.database.dao.MarketAssetDao
import com.aliayali.database.dao.NewsDao
import com.aliayali.database.model.CoinEntity
import com.aliayali.database.model.MarketAssetEntity
import com.aliayali.database.model.NewsEntity

@Database(
    entities = [
        CoinEntity::class,
        MarketAssetEntity::class,
        NewsEntity::class
    ],
    version = 2,
    exportSchema = true,
)
internal abstract class DaricDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDao

    abstract fun marketAssetDao(): MarketAssetDao

    abstract fun newsDao(): NewsDao
}