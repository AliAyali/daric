package com.aliayali.home.mapper

import com.aliayali.common.util.formattedPercent
import com.aliayali.common.util.formattedPrice
import com.aliayali.home.model.CoinUiModel
import com.aliayali.home.model.MarketAssetUiModel
import com.aliayali.model.data.Coin
import com.aliayali.model.data.MarketAsset

fun Coin.asUiModel(
    dollarToToman: Double?,
): CoinUiModel =
    CoinUiModel(
        id = id,
        symbol = symbol,
        name = name,
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

fun MarketAsset.asUiModel(): MarketAssetUiModel =
    MarketAssetUiModel(
        symbol = symbol,
        name = name,
        formattedPrice = price?.let {
            "${it.formattedPrice()} $unit"
        } ?: "-",
        formattedChange = changePercent?.let {
            "${if (it >= 0) "+" else ""}$it%"
        } ?: "-",
        price = price,
        isPositive = (changePercent ?: 0.0) >= 0,
    )