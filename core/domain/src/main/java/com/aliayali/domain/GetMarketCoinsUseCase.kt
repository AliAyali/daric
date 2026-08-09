package com.aliayali.domain

import com.aliayali.domain.repository.MarketRepository
import com.aliayali.model.data.Coin
import javax.inject.Inject

class GetMarketCoinsUseCase @Inject constructor(
    private val repository: MarketRepository,
) {
    suspend operator fun invoke(): List<Coin> {
        return repository.getCoins()
    }
}