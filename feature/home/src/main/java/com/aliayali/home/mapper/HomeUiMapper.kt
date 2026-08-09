package com.aliayali.home.mapper

import com.aliayali.common.util.formatPrice
import com.aliayali.home.model.CoinUiModel
import com.aliayali.model.data.Coin

fun Coin.asUiModel(): CoinUiModel =
    CoinUiModel(
        id = id,
        symbol = symbol,
        name = name,
        imageUrl = imageUrl,
        formattedDollarPrice = price?.formatPrice(),
        formattedTomanPrice = price?.let { (it * 180_000).formatPrice() },
        formattedChange = changePercent24h?.let {
            "${if (it >= 0) "+" else ""}$it%"
        } ?: "-",
        isPositive = (changePercent24h ?: 0.0) >= 0
    )