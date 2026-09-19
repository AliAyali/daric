package com.aliayali.data.repository

import com.aliayali.common.result.AppResult
import com.aliayali.data.error.asAppError
import com.aliayali.data.local.MarketAssetLocalDataSource
import com.aliayali.domain.repository.MarketAssetRepository
import com.aliayali.model.market.MarketAsset
import com.aliayali.network.BrsNetworkDataSource
import com.aliayali.network.model.BrsMarketItemDto
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class MarketAssetRepositoryImpl @Inject constructor(
    private val networkDataSource: BrsNetworkDataSource,
    private val localDataSource: MarketAssetLocalDataSource,
) : MarketAssetRepository {
    override fun observeMarketAsset(
        id: String,
    ): Flow<MarketAsset?> = localDataSource.observeMarketAsset(id)

    override fun observeMarketAssets(): Flow<List<MarketAsset>> =
        localDataSource.observeMarketAssets()

    override suspend fun syncMarketAssets(): AppResult<Unit> {
        return try {
            val market = networkDataSource.getMarket()

            val assets = (market.gold + market.currency).map(BrsMarketItemDto::asModel)

            localDataSource.saveMarketAssets(assets)

            AppResult.Success(Unit)
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            AppResult.Failure(
                error = error.asAppError(),
            )
        }
    }
}

fun BrsMarketItemDto.asModel(): MarketAsset = MarketAsset(
    id = symbol,
    symbol = symbol,
    name = name,
    price = price,
    changePercent = changePercent,
    unit = unit,
)