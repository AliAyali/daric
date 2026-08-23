package com.aliayali.data.local

import com.aliayali.model.market.Coin
import kotlinx.coroutines.flow.Flow

interface CoinLocalDataSource {

    fun observeCoins(): Flow<List<Coin>>

    suspend fun saveCoins(
        coins: List<Coin>,
    )
}