package com.aliayali.marketdetail.mapper

import com.aliayali.common.util.formattedPercent
import com.aliayali.common.util.formattedPrice
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
        formattedDollarPrice = null,
        formattedTomanPrice = price
            ?.formattedPrice()
            ?.let { "$it تومان" },
        formattedChange = changePercent?.let {
            "${if (it >= 0) "+" else ""}${it.formattedPrice()}%"
        } ?: "-",
        isPositive = (changePercent ?: 0.0) >= 0,
    )

fun Coin.asUiData(
    dollarToToman: Double?,
): MarketDetailUiData =
    MarketDetailUiData(
        id = id,
        name = name,
        symbol = symbol,
        imageUrl = imageUrl,
        formattedDollarPrice = price
            ?.formattedPrice()
            ?.let { "$$it" },
        formattedTomanPrice = price
            ?.let { coinPrice ->
                dollarToToman?.let { dollar ->
                    coinPrice * dollar
                }
            }
            ?.formattedPrice()
            ?.let { "$it تومان" },
        formattedChange = changePercent24h?.formattedPercent() ?: "-",
        isPositive = (changePercent24h ?: 0.0) >= 0,
    )

fun MarketPricePoint.asUiModel(): MarketPricePointUiModel =
    MarketPricePointUiModel(
        timestamp = timestamp,
        price = price.toFloat(),
    )