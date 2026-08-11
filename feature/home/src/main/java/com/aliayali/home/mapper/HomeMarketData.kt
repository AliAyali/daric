package com.aliayali.home.mapper

import com.aliayali.home.model.HomeUiData
import com.aliayali.model.HomeMarketData

fun HomeMarketData.asUiData(): HomeUiData {
    val dollarToToman = marketAssets
        .firstOrNull { it.symbol == "USD" }
        ?.price
    return HomeUiData(
        overview = marketOverview.asUiModel(),
        coins = coins.map {
            it.asUiModel(dollarToToman)
        },
        marketAssets = marketAssets.map {
            it.asUiModel()
        },
    )
}