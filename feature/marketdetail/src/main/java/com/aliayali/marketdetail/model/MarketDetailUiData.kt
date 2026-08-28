package com.aliayali.marketdetail.model

data class MarketDetailUiData(
    val id: String,
    val name: String,
    val symbol: String,
    val imageUrl: String?,
    val formattedDollarPrice: String?,
    val formattedTomanPrice: String?,
    val formattedChange: String,
    val isPositive: Boolean,
)