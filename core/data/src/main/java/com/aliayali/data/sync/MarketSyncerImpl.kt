package com.aliayali.data.sync

import com.aliayali.domain.repository.MarketAssetRepository
import com.aliayali.domain.repository.MarketRepository
import com.aliayali.domain.sync.MarketSyncer
import com.aliayali.model.result.AppResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarketSyncerImpl @Inject constructor(
    private val marketRepository: MarketRepository,
    private val marketAssetRepository: MarketAssetRepository,
) : MarketSyncer {

    override suspend fun sync(): AppResult<Unit> {

        val marketResult = marketRepository.syncMarketData()

        if (marketResult is AppResult.Failure) {
            return marketResult
        }

        val assetResult = marketAssetRepository.syncMarketAssets()

        if (assetResult is AppResult.Failure) {
            return assetResult
        }

        return AppResult.Success(Unit)
    }
}