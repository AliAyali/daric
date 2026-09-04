package com.aliayali.marketdetail.model

import androidx.annotation.DrawableRes

data class MarketDetailUiData(
    val id: String,
    val name: String,
    val symbol: String,
    val imageUrl: String?,
    @param:DrawableRes
    val icon: Int?,
    val formattedDollarPrice: String?,
    val formattedTomanPrice: String?,
    val formattedChange: String,
    val isPositive: Boolean,
)