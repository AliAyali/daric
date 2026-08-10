package com.aliayali.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BrsMarketResponseDto(
    val gold: List<BrsMarketItemDto> = emptyList(),
    val currency: List<BrsMarketItemDto> = emptyList(),
)

@Serializable
data class BrsMarketItemDto(
    val symbol: String,
    val name: String,
    val price: Double,
    @SerialName("change_percent")
    val changePercent: Double,
    val unit: String,
)