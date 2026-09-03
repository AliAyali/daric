package com.aliayali.market.mapper

import com.aliayali.common.util.formattedPercent
import com.aliayali.common.util.formattedPrice
import com.aliayali.market.model.MarketItemUiModel
import com.aliayali.model.market.Coin
import com.aliayali.model.market.MarketAsset

fun Coin.asUiModel(
    dollarToToman: Double?,
): MarketItemUiModel.Coin {
    return MarketItemUiModel.Coin(
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
        isPositive = changePercent24h?.let { it >= 0 } ?: true,
    )
}

fun MarketAsset.asUiModel(): MarketItemUiModel.MarketAsset {
    return MarketItemUiModel.MarketAsset(
        id = id,
        name = name,
        symbol = symbol,
        formattedPrice = price?.let {
            "${it.formattedPrice()} $unit"
        } ?: "-",
        formattedChange = changePercent?.let {
            "${if (it >= 0) "+" else ""}$it%"
        } ?: "-",
        isPositive =
            (changePercent ?: 0.0) >= 0,
    )
}