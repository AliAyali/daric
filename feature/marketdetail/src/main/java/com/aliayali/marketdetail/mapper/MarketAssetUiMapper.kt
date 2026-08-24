package com.aliayali.marketdetail.mapper

import com.aliayali.marketdetail.model.MarketDetailUiData
import com.aliayali.model.market.Coin
import com.aliayali.model.market.MarketAsset

fun MarketAsset.asUiData(): MarketDetailUiData =
    MarketDetailUiData(
        id = id,
        name = name,
        symbol = symbol,
        imageUrl = null,
        price = price,
        changePercent = changePercent,
        unit = unit,
    )

fun Coin.asUiData(): MarketDetailUiData =
    MarketDetailUiData(
        id = id,
        name = name,
        symbol = symbol,
        imageUrl = imageUrl,
        price = price,
        changePercent = changePercent24h,
        unit = null,
    )