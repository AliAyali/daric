package com.aliayali.home.model

data class CoinUiModel(
    val id: String,
    val symbol: String,
    val name: String,
    val formattedDollarPrice: String?,
    val formattedTomanPrice: String?,
    val formattedChange: String,
    val isPositive: Boolean,
)