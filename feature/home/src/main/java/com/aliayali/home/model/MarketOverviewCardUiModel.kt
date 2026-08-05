package com.aliayali.home.model

data class MarketOverviewCardUiModel(
    val marketStatus: MarketStatus,
    val insightTitle: String,
    val insightDescription: String,
    val usd: CoinUiModel,
    val gold18: CoinUiModel,
)