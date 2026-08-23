package com.aliayali.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aliayali.model.market.MarketAsset

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

fun MarketAssetEntity.asModel(): MarketAsset =
    MarketAsset(
        id = id,
        symbol = symbol,
        name = name,
        price = price,
        changePercent = changePercent,
        unit = unit,
    )

fun MarketAsset.asEntity(): MarketAssetEntity =
    MarketAssetEntity(
        id = id,
        symbol = symbol,
        name = name,
        price = price,
        changePercent = changePercent,
        unit = unit,
    )