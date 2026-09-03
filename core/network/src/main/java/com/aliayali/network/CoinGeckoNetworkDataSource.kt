package com.aliayali.network

import android.util.Log
import com.aliayali.model.market.Coin
import com.aliayali.network.error.safeNetworkCall
import com.aliayali.network.model.CoinGeckoCoinDto
import com.aliayali.network.model.CoinGeckoSearchCoinDto
import com.aliayali.network.model.CoinMarketChartDto
import com.aliayali.network.retrofit.CoinGeckoApi
import javax.inject.Inject
import javax.inject.Singleton

interface CoinGeckoNetworkDataSource {
    suspend fun getMarketCoins(
        perPage: Int = 50,
        page: Int = 1,
    ): List<CoinGeckoCoinDto>

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
    override suspend fun getMarketCoins(
        perPage: Int,
        page: Int,
    ): List<CoinGeckoCoinDto> {
        return api.getMarketCoins(
            perPage = perPage,
            page = page,
        )
    }

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