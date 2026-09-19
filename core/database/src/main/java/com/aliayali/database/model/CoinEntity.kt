package com.aliayali.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aliayali.model.market.Coin

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

fun CoinEntity.asModel(): Coin =
    Coin(
        id = id,
        symbol = symbol,
        name = name,
        imageUrl = imageUrl,
        price = price,
        changePercent24h = changePercent24h,
    )

fun Coin.asEntity(): CoinEntity =
    CoinEntity(
        id = id,
        symbol = symbol,
        name = name,
        imageUrl = imageUrl,
        price = price,
        changePercent24h = changePercent24h,
    )