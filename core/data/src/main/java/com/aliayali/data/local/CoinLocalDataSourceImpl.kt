package com.aliayali.data.local

import com.aliayali.database.dao.CoinDao
import com.aliayali.database.mapper.asEntity
import com.aliayali.database.mapper.asModel
import com.aliayali.model.market.Coin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoinLocalDataSourceImpl @Inject constructor(
    private val coinDao: CoinDao,
) : CoinLocalDataSource {

    override fun observeCoins(): Flow<List<Coin>> =
        coinDao.observeAll()
            .map { entities ->
                entities.map { it.asModel() }
            }

    override suspend fun saveCoins(
        coins: List<Coin>,
    ) {
        coinDao.upsertAll(
            coins.map { it.asEntity() },
        )
    }
}