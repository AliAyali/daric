package com.aliayali.model.analysis

import com.aliayali.model.market.MarketAsset

data class MarketOverview(
    val marketStatus: MarketStatus,
    val insightTitle: String,
    val insightDescription: String,
    val usd: MarketAsset,
    val gold18: MarketAsset,
)

enum class MarketStatus {
    Bullish,
    Bearish,
    Volatile,
}