package com.aliayali.domain

import com.aliayali.domain.repository.MarketAssetRepository
import com.aliayali.model.data.MarketAsset
import javax.inject.Inject

class GetMarketAssetsUseCase @Inject constructor(
    private val repository: MarketAssetRepository,
) {
    suspend operator fun invoke(): List<MarketAsset> {
        return repository.getMarketAssets()
    }
}