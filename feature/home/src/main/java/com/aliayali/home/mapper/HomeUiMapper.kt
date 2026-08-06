package com.aliayali.home.mapper

import com.aliayali.home.model.CoinUiModel
import com.aliayali.home.model.MarketOverviewCardUiModel
import com.aliayali.home.model.MarketSectionCardUiModel
import com.aliayali.home.model.MarketStatus
import com.aliayali.model.data.Coin
import com.aliayali.model.data.MarketOverview
import com.aliayali.model.data.MarketSection

fun Coin.asUiModel(): CoinUiModel {
    return CoinUiModel(
        id = id,
        symbol = symbol,
        name = name,
        formattedDollarPrice = dollarPrice?.let { "$$it" },
        formattedTomanPrice = tomanPrice?.let { "$it ت" },
        formattedChange = "${if (changePercent >= 0) "+" else ""}$changePercent%",
        isPositive = changePercent >= 0,
    )
}

fun MarketOverview.asUiModel(): MarketOverviewCardUiModel {
    return MarketOverviewCardUiModel(
        marketStatus = MarketStatus.Volatile,
        insightTitle = insightTitle,
        insightDescription = insightDescription,
        usd = usd.asUiModel(),
        gold18 = gold18.asUiModel(),
    )
}

fun MarketSection.asUiModel() =
    MarketSectionCardUiModel(
        category = category,
        items = items.map(Coin::asUiModel),
    )