package com.aliayali.home.mapper

import com.aliayali.common.util.formattedPercent
import com.aliayali.common.util.formattedPrice
import com.aliayali.home.model.CoinUiModel
import com.aliayali.model.data.Coin

fun Coin.asUiModel(): CoinUiModel =
    CoinUiModel(
        id = id,
        symbol = symbol,
        name = name,
        imageUrl = imageUrl,
        formattedDollarPrice = price?.formattedPrice()?.let { "$$it" },
        formattedTomanPrice = price
            ?.let { (it * 180_000).formattedPrice() }
            ?.let { "$it تومان" },
        formattedChange = changePercent24h?.formattedPercent() ?: "-",
        isPositive = (changePercent24h ?: 0.0) >= 0
    )