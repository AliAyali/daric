package com.aliayali.model.analysis

import com.aliayali.model.market.MarketAsset

data class MarketOverview(
    val analysis: MarketAnalysis,
    val usd: MarketAsset,
    val gold18: MarketAsset,
)