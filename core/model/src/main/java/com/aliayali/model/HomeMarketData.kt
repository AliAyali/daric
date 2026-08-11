package com.aliayali.model

import com.aliayali.model.analysis.MarketOverview
import com.aliayali.model.market.Coin
import com.aliayali.model.market.MarketAsset

data class HomeMarketData(
    val marketOverview: MarketOverview,
    val coins: List<Coin>,
    val marketAssets: List<MarketAsset>,
)