package com.aliayali.model.analysis

import com.aliayali.model.market.Coin
import com.aliayali.model.market.MarketAsset

data class MarketSnapshot(
    val usd: MarketAsset,
    val gold18: MarketAsset,
    val goldOunce: MarketAsset,
    val btc: Coin,
)