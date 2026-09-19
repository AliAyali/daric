package com.aliayali.model.market

data class Coin(
    val id: String,
    val symbol: String,
    val name: String,
    val imageUrl: String,
    val price: Double?,
    val changePercent24h: Double?,
)