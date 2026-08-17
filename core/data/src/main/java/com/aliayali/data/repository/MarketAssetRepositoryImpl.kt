package com.aliayali.data.repository

import com.aliayali.data.error.asAppError
import com.aliayali.domain.repository.MarketAssetRepository
import com.aliayali.model.market.MarketAsset
import com.aliayali.model.result.AppResult
import com.aliayali.network.BrsNetworkDataSource
import com.aliayali.network.model.BrsMarketItemDto
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.cancellation.CancellationException

@Singleton
class MarketAssetRepositoryImpl @Inject constructor(
    private val networkDataSource: BrsNetworkDataSource,
) : MarketAssetRepository {

    override suspend fun getMarketAssets(): AppResult<List<MarketAsset>> {
        return try {
            val market = networkDataSource.getMarket()
            AppResult.Success(
                data = (market.gold + market.currency)
                    .map(BrsMarketItemDto::asModel)
            )
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            AppResult.Failure(
                error = error.asAppError(),
            )
        }
    }
}

fun BrsMarketItemDto.asModel(): MarketAsset =
    MarketAsset(
        id = symbol,
        symbol = symbol,
        name = name,
        price = price,
        changePercent = changePercent,
        unit = unit,
    )