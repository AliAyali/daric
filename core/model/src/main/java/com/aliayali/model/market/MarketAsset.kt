package com.aliayali.model.market

data class MarketAsset(
    val id: String,
    val symbol: String,
    val name: String,
    val price: Double?,
    val changePercent: Double?,
    val unit: String,
)