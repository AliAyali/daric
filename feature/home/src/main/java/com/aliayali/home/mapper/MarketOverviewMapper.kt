package com.aliayali.home.mapper

import com.aliayali.home.model.MarketOverviewCardUiModel
import com.aliayali.model.analysis.MarketOverview
import com.aliayali.home.model.MarketStatus as UiMarketStatus
import com.aliayali.model.analysis.MarketStatus as DomainMarketStatus

fun MarketOverview.asUiModel(): MarketOverviewCardUiModel =
    MarketOverviewCardUiModel(
        marketStatus = marketStatus.asUiModel(),
        insightTitle = insightTitle,
        insightDescription = insightDescription,
        usd = usd.asUiModel(),
        gold18 = gold18.asUiModel(),
    )

private fun DomainMarketStatus.asUiModel(): UiMarketStatus =
    when (this) {
        DomainMarketStatus.Bullish ->
            UiMarketStatus.Normal

        DomainMarketStatus.Bearish ->
            UiMarketStatus.Critical

        DomainMarketStatus.Volatile ->
            UiMarketStatus.Volatile
    }