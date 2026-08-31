package com.aliayali.search.mapper

import com.aliayali.common.util.formattedPercent
import com.aliayali.common.util.formattedPrice
import com.aliayali.model.market.SearchResult
import com.aliayali.search.model.SearchItemUiModel

fun SearchResult.asUiModel(): SearchItemUiModel =
    when (this) {

        is SearchResult.CoinResult -> {
            SearchItemUiModel.Coin(
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
                formattedChange =
                    changePercent24h
                        ?.formattedPercent()
                        ?: "-",
                isPositive =
                    (changePercent24h ?: 0.0) >= 0,
            )
        }

        is SearchResult.MarketAssetResult -> {
            SearchItemUiModel.MarketAsset(
                id = id,
                symbol = symbol,
                name = name,
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
    }