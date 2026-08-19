package com.aliayali.data.local

import com.aliayali.database.dao.MarketAssetDao
import com.aliayali.database.mapper.asEntity
import com.aliayali.database.mapper.asModel
import com.aliayali.model.market.MarketAsset
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MarketAssetLocalDataSourceImpl @Inject constructor(
    private val marketAssetDao: MarketAssetDao,
) : MarketAssetLocalDataSource {

    override fun observeMarketAssets(): Flow<List<MarketAsset>> =
        marketAssetDao
            .observeAll()
            .map { entities ->
                entities.map { it.asModel() }
            }

    override suspend fun saveMarketAssets(
        assets: List<MarketAsset>,
    ) {
        marketAssetDao.upsertAll(
            assets.map { it.asEntity() },
        )
    }
}