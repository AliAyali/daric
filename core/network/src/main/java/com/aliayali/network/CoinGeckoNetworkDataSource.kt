package com.aliayali.network

import com.aliayali.model.market.Coin
import com.aliayali.network.error.safeNetworkCall
import com.aliayali.network.model.CoinGeckoCoinDto
import com.aliayali.network.model.CoinGeckoSearchCoinDto
import com.aliayali.network.model.CoinMarketChartDto
import com.aliayali.network.retrofit.CoinGeckoApi
import javax.inject.Inject
import javax.inject.Singleton

interface CoinGeckoNetworkDataSource {

    suspend fun getMarketChart(
        id: String,
        days: Int,
    ): CoinMarketChartDto

    suspend fun getMarkets(
        ids: String,
    ): List<CoinGeckoCoinDto>

    suspend fun searchCoins(
        query: String,
    ): List<CoinGeckoSearchCoinDto>
}

@Singleton
class RetrofitCoinGeckoNetworkDataSource @Inject constructor(
    private val api: CoinGeckoApi,
) : CoinGeckoNetworkDataSource {

    override suspend fun getMarketChart(
        id: String,
        days: Int,
    ): CoinMarketChartDto = api.getMarketChart(
        id = id,
        days = days,
    )

    override suspend fun getMarkets(
        ids: String,
    ): List<CoinGeckoCoinDto> = safeNetworkCall {
        api.getMarkets(
            ids = ids,
        )
    }

    override suspend fun searchCoins(
        query: String,
    ): List<CoinGeckoSearchCoinDto> =
        safeNetworkCall {
            api.search(
                query = query,
            )
        }.coins
}