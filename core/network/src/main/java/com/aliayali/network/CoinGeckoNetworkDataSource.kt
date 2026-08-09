package com.aliayali.network

import com.aliayali.network.model.CoinGeckoCoinDto
import javax.inject.Inject
import javax.inject.Singleton

interface CoinGeckoNetworkDataSource {
    suspend fun getMarkets(
        ids: String,
    ): List<CoinGeckoCoinDto>
}

@Singleton
class RetrofitCoinGeckoNetworkDataSource @Inject constructor(
    private val api: CoinGeckoApi,
) : CoinGeckoNetworkDataSource {
    override suspend fun getMarkets(ids: String): List<CoinGeckoCoinDto> =
        api.getMarkets(ids = ids)
}