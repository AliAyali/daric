package com.aliayali.home.model

data class CoinUiModel(
    val id: String,
    val name: String,
    val formattedPrice: String,
    val imageUrl: String,
    val isPositive: Boolean,
)