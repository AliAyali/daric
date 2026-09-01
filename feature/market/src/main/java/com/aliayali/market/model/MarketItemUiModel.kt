package com.aliayali.market.model

data class MarketItemUiModel(
    val id: String,
    val symbol: String,
    val name: String,
    val imageUrl: String,
    val formattedDollarPrice: String?,
    val formattedTomanPrice: String?,
    val formattedChange: String,
    val isPositive: Boolean,
)