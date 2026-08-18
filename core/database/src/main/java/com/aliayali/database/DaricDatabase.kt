package com.aliayali.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aliayali.database.dao.CoinDao
import com.aliayali.database.model.CoinEntity

@Database(
    entities = [
        CoinEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class DaricDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDao
}