package com.aliayali.domain.repository

import com.aliayali.model.data.Coin

interface MarketRepository {
    suspend fun getCoins(): List<Coin>
}