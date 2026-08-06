package com.aliayali.model.data

data class Coin(
    val id: String,
    val symbol: String,
    val name: String,
    val dollarPrice: Double?,
    val tomanPrice: Long?,
    val changePercent: Double,
)