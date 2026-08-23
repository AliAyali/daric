package com.aliayali.home.model

data class MarketAssetUiModel(
    val symbol: String,
    val name: String,
    val price: Double?,
    val formattedPrice: String,
    val formattedChange: String,
    val isPositive: Boolean,
)