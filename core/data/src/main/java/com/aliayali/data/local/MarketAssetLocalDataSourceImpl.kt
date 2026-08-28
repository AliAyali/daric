package com.aliayali.data.local

import com.aliayali.database.dao.MarketAssetDao
import com.aliayali.database.model.MarketAssetEntity
import com.aliayali.database.model.asEntity
import com.aliayali.database.model.asModel
import com.aliayali.model.market.MarketAsset
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class MarketAssetLocalDataSourceImpl @Inject constructor(
    private val marketAssetDao: MarketAssetDao,
) : MarketAssetLocalDataSource {

    override fun observeMarketAsset(
        id: String,
    ): Flow<MarketAsset?> =
        marketAssetDao
            .observeById(id)
            .map { it?.asModel() }

    override fun observeMarketAssets(): Flow<List<MarketAsset>> =
        marketAssetDao.observeAll()
            .map { it.map(MarketAssetEntity::asModel) }

    override suspend fun saveMarketAssets(
        assets: List<MarketAsset>,
    ) {
        marketAssetDao.upsertAll(
            assets.map(MarketAsset::asEntity),
        )
    }
}