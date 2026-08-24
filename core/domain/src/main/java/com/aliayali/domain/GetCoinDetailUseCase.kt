package com.aliayali.domain

import com.aliayali.domain.repository.MarketRepository
import com.aliayali.model.market.Coin
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(
    private val marketRepository: MarketRepository,
) {

    operator fun invoke(
        id: String,
    ): Flow<Coin?> = marketRepository.observeMarketData(id)
}