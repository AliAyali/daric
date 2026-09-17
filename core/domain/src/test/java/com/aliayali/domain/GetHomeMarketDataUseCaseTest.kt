package com.aliayali.domain

import com.aliayali.common.result.AppResult
import com.aliayali.domain.repository.MarketAssetRepository
import com.aliayali.domain.repository.MarketRepository
import com.aliayali.model.analysis.MarketAnalysis
import com.aliayali.model.analysis.MarketCondition
import com.aliayali.model.analysis.MarketSnapshot
import com.aliayali.model.market.Coin
import com.aliayali.model.market.MarketAsset
import com.aliayali.model.market.MarketData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetHomeMarketDataUseCaseTest {

    private lateinit var marketRepository: FakeMarketRepository
    private lateinit var marketAssetRepository: FakeMarketAssetRepository
    private lateinit var useCase: GetHomeMarketDataUseCase

    @Before
    fun setup() {
        marketRepository = FakeMarketRepository()
        marketAssetRepository = FakeMarketAssetRepository()

        val marketAnalyzer = object : MarketAnalyzer {
            override fun analyze(
                snapshot: MarketSnapshot,
            ): MarketAnalysis {
                return MarketAnalysis(
                    condition = MarketCondition.Calm,
                    score = 0.5,
                    confidence = 0.8,
                    signals = emptyList(),
                    reasons = emptyList(),
                )
            }
        }

        val getMarketOverviewUseCase = GetMarketOverviewUseCase(
            marketAnalyzer = marketAnalyzer,
        )

        useCase = GetHomeMarketDataUseCase(
            marketRepository = marketRepository,
            marketAssetRepository = marketAssetRepository,
            getMarketOverviewUseCase = getMarketOverviewUseCase,
        )
    }

    @Test
    fun `observeHomeMarketData keeps only first five coins`() = runTest {
        marketRepository.marketData = MarketData(
            coins = listOf(
                createCoin("coin-1"),
                createCoin("coin-2"),
                createCoin("coin-3"),
                createCoin("coin-4"),
                createCoin("coin-5"),
                createCoin("coin-6"),
                createCoin("coin-7"),
            ),
        )

        marketAssetRepository.assets = requiredAssets()

        val result = useCase.observeHomeMarketData().single()

        assertEquals(5, result.coins.size)

        assertEquals(
            listOf(
                "coin-1",
                "coin-2",
                "coin-3",
                "coin-4",
                "coin-5",
            ),
            result.coins.map { it.id },
        )
    }

    @Test
    fun `observeHomeMarketData emits nothing when coins are empty`() = runTest {
        marketRepository.marketData = MarketData(
            coins = emptyList(),
        )

        marketAssetRepository.assets = requiredAssets()

        val result = useCase.observeHomeMarketData()

        assertTrue(
            result
                .toList()
                .isEmpty(),
        )
    }

    @Test
    fun `observeHomeMarketData emits nothing when required assets are missing`() = runTest {
        marketRepository.marketData = MarketData(
            coins = listOf(
                createCoin("coin-1"),
            ),
        )

        marketAssetRepository.assets = listOf(
            createAsset(
                id = "usd",
                symbol = "USD",
            ),
            createAsset(
                id = "gold",
                symbol = "IR_GOLD_18K",
            ),
        )

        val result = useCase.observeHomeMarketData()

        assertTrue(
            result
                .toList()
                .isEmpty(),
        )
    }

    @Test
    fun `observeHomeMarketData creates home data when required data exists`() = runTest {
        val coins = listOf(
            createCoin("coin-1"),
            createCoin("coin-2"),
        )

        val assets = requiredAssets()

        marketRepository.marketData = MarketData(
            coins = coins,
        )

        marketAssetRepository.assets = assets

        val result = useCase.observeHomeMarketData().single()

        assertEquals(coins, result.coins)
        assertEquals(assets, result.marketAssets)

        assertEquals(
            MarketCondition.Calm,
            result.marketOverview.analysis.condition,
        )

        assertEquals(
            0.5,
            result.marketOverview.analysis.score,
            0.0,
        )

        assertEquals(
            0.8,
            result.marketOverview.analysis.confidence,
            0.0,
        )
    }

    private fun requiredAssets(): List<MarketAsset> {
        return listOf(
            createAsset(
                id = "usd",
                symbol = "USD",
                name = "Dollar",
            ),
            createAsset(
                id = "gold18",
                symbol = "IR_GOLD_18K",
                name = "Gold 18K",
            ),
            createAsset(
                id = "gold-ounce",
                symbol = "XAUUSD",
                name = "Gold Ounce",
            ),
        )
    }

    private fun createCoin(
        id: String,
    ) = Coin(
        id = id,
        symbol = "BTC",
        name = "Bitcoin",
        imageUrl = "image-url",
        price = 100.0,
        changePercent24h = 2.0,
    )

    private fun createAsset(
        id: String,
        symbol: String,
        name: String = "Asset",
    ) = MarketAsset(
        id = id,
        symbol = symbol,
        name = name,
        price = 100.0,
        changePercent = 1.0,
        unit = "IRT",
    )

    private class FakeMarketRepository : MarketRepository {

        var marketData = MarketData(
            coins = emptyList(),
        )

        override suspend fun getCoin(id: String): Coin? = null

        override suspend fun getCoinPriceHistory(
            id: String,
            days: Int,
        ) = emptyList<com.aliayali.model.market.MarketPricePoint>()

        override suspend fun syncMarketCoins(
            perPage: Int,
            page: Int,
        ): AppResult<Unit> {
            TODO("Not needed for this test")
        }

        override fun observeMarketData(id: String): Flow<Coin?> {
            TODO("Not needed for this test")
        }

        override fun observeMarketData(): Flow<MarketData> =
            flowOf(marketData)

        override suspend fun syncMarketData(): AppResult<Unit> {
            TODO("Not needed for this test")
        }

        override suspend fun searchCoins(query: String): List<Coin> =
            emptyList()
    }

    private class FakeMarketAssetRepository : MarketAssetRepository {

        var assets = emptyList<MarketAsset>()

        override fun observeMarketAsset(id: String): Flow<MarketAsset?> {
            TODO("Not needed for this test")
        }

        override fun observeMarketAssets(): Flow<List<MarketAsset>> =
            flowOf(assets)

        override suspend fun syncMarketAssets(): AppResult<Unit> {
            TODO("Not needed for this test")
        }
    }
}