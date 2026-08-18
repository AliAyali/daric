package com.aliayali.database.mapper

import com.aliayali.database.model.CoinEntity
import com.aliayali.model.market.Coin

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