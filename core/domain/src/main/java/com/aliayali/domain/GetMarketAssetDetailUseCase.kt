package com.aliayali.domain

import com.aliayali.domain.repository.MarketAssetRepository
import com.aliayali.model.market.MarketAsset
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMarketAssetDetailUseCase @Inject constructor(
    private val marketAssetRepository: MarketAssetRepository,
) {

    operator fun invoke(
        id: String,
    ): Flow<MarketAsset?> =
        marketAssetRepository.observeMarketAsset(id)
}