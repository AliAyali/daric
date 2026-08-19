package com.aliayali.database.mapper

import com.aliayali.database.model.MarketAssetEntity
import com.aliayali.model.market.MarketAsset

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