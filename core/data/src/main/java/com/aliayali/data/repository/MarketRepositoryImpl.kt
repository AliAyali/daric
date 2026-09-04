package com.aliayali.data.repository

import com.aliayali.common.result.AppResult
import com.aliayali.data.config.MarketConfig.defaultCoinIds
import com.aliayali.data.error.asAppError
import com.aliayali.data.local.CoinLocalDataSource
import com.aliayali.domain.repository.MarketRepository
import com.aliayali.model.market.Coin
import com.aliayali.model.market.MarketData
import com.aliayali.model.market.MarketPricePoint
import com.aliayali.network.CoinGeckoNetworkDataSource
import com.aliayali.network.model.CoinGeckoCoinDto
import com.aliayali.network.model.CoinMarketChartDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.cancellation.CancellationException

@Singleton
internal class MarketRepositoryImpl @Inject constructor(
    private val networkDataSource: CoinGeckoNetworkDataSource,
    private val localDataSource: CoinLocalDataSource,
) : MarketRepository {
    override suspend fun syncMarketCoins(
        perPage: Int,
        page: Int,
    ): AppResult<Unit> {
        return try {
            val coins = networkDataSource
                .getMarketCoins(
                    perPage = perPage,
                    page = page,
                )
                .map(CoinGeckoCoinDto::asModel)
            localDataSource.saveCoins(coins)

            AppResult.Success(Unit)
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            AppResult.Failure(error = error.asAppError())
        }
    }

    override suspend fun getCoin(
        id: String,
    ): Coin? {
        return networkDataSource
            .getMarkets(ids = id)
            .firstOrNull()
            ?.asModel()
    }

    override fun observeMarketData(
        id: String,
    ): Flow<Coin?> =
        localDataSource.observeCoin(id)

    override fun observeMarketData(): Flow<MarketData> =
        localDataSource
            .observeCoins()
            .map(::MarketData)

    override suspend fun syncMarketData(): AppResult<Unit> {
        return try {
            val coins = networkDataSource
                .getMarkets(
                    ids = defaultCoinIds.joinToString(","),
                )
                .map(CoinGeckoCoinDto::asModel)

            localDataSource.saveCoins(coins)

            AppResult.Success(Unit)
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            AppResult.Failure(
                error = error.asAppError(),
            )
        }
    }

    override suspend fun getCoinPriceHistory(
        id: String,
        days: Int,
    ): List<MarketPricePoint> {
        return networkDataSource
            .getMarketChart(
                id = id,
                days = days,
            )
            .asMarketPricePoints()
    }

    override suspend fun searchCoins(
        query: String,
    ): List<Coin> {
        val searchResults = networkDataSource
            .searchCoins(query)
            .take(7)

        if (searchResults.isEmpty()) {
            return emptyList()
        }

        val ids = searchResults
            .joinToString(",") { it.id }

        return networkDataSource
            .getMarkets(ids = ids)
            .map(CoinGeckoCoinDto::asModel)
    }
}

fun CoinGeckoCoinDto.asModel(): Coin =
    Coin(
        id = id,
        symbol = symbol,
        name = name,
        price = currentPrice,
        changePercent24h = priceChangePercentage24h,
        imageUrl = image,
    )

fun CoinMarketChartDto.asMarketPricePoints(): List<MarketPricePoint> =
    prices.mapNotNull { point ->
        if (point.size < 2) {
            return@mapNotNull null
        }

        MarketPricePoint(
            timestamp = point[0].toLong(),
            price = point[1],
        )
    }