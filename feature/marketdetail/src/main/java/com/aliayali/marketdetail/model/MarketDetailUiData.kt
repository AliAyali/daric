package com.aliayali.marketdetail.model

data class MarketDetailUiData(
    val id: String,
    val name: String,
    val symbol: String,
    val imageUrl: String?,
    val price: Double?,
    val changePercent: Double?,
    val unit: String?,
)