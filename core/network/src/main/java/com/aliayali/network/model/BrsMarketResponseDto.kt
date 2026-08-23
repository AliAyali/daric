package com.aliayali.network.model

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class BrsMarketResponseDto(
    val gold: List<BrsMarketItemDto> = emptyList(),
    val currency: List<BrsMarketItemDto> = emptyList(),
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class BrsMarketItemDto(
    val symbol: String,
    val name: String,
    val price: Double,
    @SerialName("change_percent")
    val changePercent: Double,
    val unit: String,
)