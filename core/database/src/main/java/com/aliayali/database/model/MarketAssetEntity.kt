package com.aliayali.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "market_assets")
data class MarketAssetEntity(
    @PrimaryKey
    val id: String,
    val symbol: String,
    val name: String,
    val price: Double?,
    val changePercent: Double?,
    val unit: String,
)