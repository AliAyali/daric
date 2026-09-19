package com.aliayali.data.local

import com.aliayali.database.dao.CoinDao
import com.aliayali.database.model.CoinEntity
import com.aliayali.database.model.asEntity
import com.aliayali.database.model.asModel
import com.aliayali.model.market.Coin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class CoinLocalDataSourceImpl @Inject constructor(
    private val coinDao: CoinDao,
) : CoinLocalDataSource {
    override fun observeCoin(
        id: String,
    ): Flow<Coin?> =
        coinDao
            .observeById(id)
            .map { it?.asModel() }

    override fun observeCoins(): Flow<List<Coin>> =
        coinDao.observeAll()
            .map { it.map(CoinEntity::asModel) }

    override suspend fun saveCoins(
        coins: List<Coin>,
    ) {
        coinDao.upsertAll(
            coins.map(Coin::asEntity),
        )
    }
}