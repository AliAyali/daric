package com.aliayali.marketdetail.mapper

import com.aliayali.marketdetail.model.MarketDetailUiData
import com.aliayali.marketdetail.model.MarketPricePointUiModel
import com.aliayali.model.market.Coin
import com.aliayali.model.market.MarketAsset
import com.aliayali.model.market.MarketPricePoint

fun MarketAsset.asUiData(): MarketDetailUiData =
    MarketDetailUiData(
        id = id,
        name = name,
        symbol = symbol,
        imageUrl = null,
        formattedPrice = price?.toString() ?: "-",
        formattedChange = changePercent?.toString() ?: "-",
        isPositive = changePercent?.let { it >= 0 } ?: false,
    )

fun Coin.asUiData(): MarketDetailUiData =
    MarketDetailUiData(
        id = id,
        name = name,
        symbol = symbol,
        imageUrl = imageUrl,
        formattedPrice = price?.toString() ?: "-",
        formattedChange = changePercent24h?.toString() ?: "-",
        isPositive = changePercent24h?.let { it >= 0 } ?: false,
    )

fun MarketPricePoint.asUiModel(): MarketPricePointUiModel =
    MarketPricePointUiModel(
        timestamp = timestamp,
        price = price.toFloat(),
    )