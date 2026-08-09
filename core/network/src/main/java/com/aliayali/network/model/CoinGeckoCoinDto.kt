package com.aliayali.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinGeckoCoinDto(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    @SerialName("current_price")
    val currentPrice: Double?,
    @SerialName("price_change_percentage_24h")
    val priceChangePercentage24h: Double?,
)