package com.aliayali.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class CoinEntity(
    @PrimaryKey
    val id: String,
    val symbol: String,
    val name: String,
    val imageUrl: String,
    val price: Double?,
    val changePercent24h: Double?,
)