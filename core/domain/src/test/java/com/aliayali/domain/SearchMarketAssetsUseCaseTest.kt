package com.aliayali.domain

import com.aliayali.common.result.AppResult
import com.aliayali.domain.repository.MarketAssetRepository
import com.aliayali.domain.repository.MarketRepository
import com.aliayali.model.market.Coin
import com.aliayali.model.market.MarketAsset
import com.aliayali.model.market.SearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SearchMarketAssetsUseCaseTest {

    private lateinit var useCase: SearchMarketAssetsUseCase

    private lateinit var marketRepository: FakeMarketRepository
    private lateinit var marketAssetRepository: FakeMarketAssetRepository

    @Before
    fun setup() {
        marketRepository = FakeMarketRepository()
        marketAssetRepository = FakeMarketAssetRepository()

        useCase = SearchMarketAssetsUseCase(
            marketRepository = marketRepository,
            marketAssetRepository = marketAssetRepository,
        )
    }

    @Test
    fun `invoke returns empty list when query is blank`() = runTest {
        val result = useCase("   ").single()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `invoke trims query before searching coins`() = runTest {
        marketRepository.searchResults = listOf(
            createCoin(
                id = "bitcoin",
                name = "Bitcoin",
            ),
        )

        val result = useCase("  bitcoin  ").single()

        assertEquals("bitcoin", marketRepository.lastSearchQuery)
        assertEquals(1, result.size)
    }

    @Test
    fun `invoke returns coin result with dollar to toman price`() = runTest {
        marketRepository.searchResults = listOf(
            createCoin(
                id = "bitcoin",
                symbol = "BTC",
                name = "Bitcoin",
                price = 100_000.0,
                changePercent24h = 2.5,
            ),
        )

        marketAssetRepository.assets = listOf(
            createAsset(
                id = "USD",
                symbol = "USD",
                name = "Dollar",
                price = 100_000.0,
            ),
        )

        val result = useCase("bitcoin").single()

        val coinResult = result.single() as SearchResult.CoinResult

        assertEquals("bitcoin", coinResult.id)
        assertEquals("BTC", coinResult.symbol)
        assertEquals("Bitcoin", coinResult.name)
        coinResult.price?.let { assertEquals(100_000.0, it, 0.0) }
        coinResult.changePercent24h?.let { assertEquals(2.5, it, 0.0) }
        coinResult.dollarToToman?.let { assertEquals(100_000.0, it, 0.0) }
    }

    @Test
    fun `invoke returns market assets matching name or symbol ignoring case`() = runTest {
        marketRepository.searchResults = emptyList()

        marketAssetRepository.assets = listOf(
            createAsset(
                id = "usd",
                symbol = "USD",
                name = "Dollar",
            ),
            createAsset(
                id = "gold",
                symbol = "XAUUSD",
                name = "Gold Ounce",
            ),
            createAsset(
                id = "eur",
                symbol = "EUR",
                name = "Euro",
            ),
        )

        val result = useCase("gold").single()

        assertEquals(1, result.size)

        val assetResult = result.single() as SearchResult.MarketAssetResult

        assertEquals("gold", assetResult.id)
        assertEquals("XAUUSD", assetResult.symbol)
        assertEquals("Gold Ounce", assetResult.name)
    }

    @Test
    fun `invoke combines coins and market assets and sorts results`() = runTest {
        marketRepository.searchResults = listOf(
            createCoin(
                id = "z-coin",
                symbol = "ZCO",
                name = "Zeta",
            ),
            createCoin(
                id = "a-coin",
                symbol = "ACO",
                name = "Alpha",
            ),
        )

        marketAssetRepository.assets = listOf(
            createAsset(
                id = "beta",
                symbol = "BET",
                name = "Market Beta",
            ),
            createAsset(
                id = "alpha-asset",
                symbol = "ALP",
                name = "Market Alpha",
            ),
        )

        val result = useCase("market").single()

        assertEquals(4, result.size)

        assertEquals(
            listOf(
                "Alpha",
                "Market Alpha",
                "Market Beta",
                "Zeta",
            ),
            result.map { it.name }
        )
    }

    @Test
    fun `invoke returns null dollar to toman when USD asset is missing`() = runTest {
        marketRepository.searchResults = listOf(
            createCoin(
                id = "bitcoin",
                name = "Bitcoin",
            ),
        )

        marketAssetRepository.assets = listOf(
            createAsset(
                id = "gold",
                symbol = "XAUUSD",
                name = "Gold",
            ),
        )

        val result = useCase("bitcoin").single()

        val coinResult = result.single() as SearchResult.CoinResult

        assertEquals(null, coinResult.dollarToToman)
    }

    private fun createCoin(
        id: String = "coin-id",
        symbol: String = "BTC",
        name: String = "Bitcoin",
        price: Double? = 100.0,
        changePercent24h: Double? = 1.0,
    ) = Coin(
        id = id,
        symbol = symbol,
        name = name,
        imageUrl = "image-url",
        price = price,
        changePercent24h = changePercent24h,
    )

    private fun createAsset(
        id: String = "asset-id",
        symbol: String = "USD",
        name: String = "Dollar",
        price: Double? = 100.0,
    ) = MarketAsset(
        id = id,
        symbol = symbol,
        name = name,
        price = price,
        changePercent = 1.0,
        unit = "IRT",
    )

    private class FakeMarketRepository : MarketRepository {

        var searchResults: List<Coin> = emptyList()
        var lastSearchQuery: String? = null

        override suspend fun getCoin(id: String): Coin? = null

        override suspend fun getCoinPriceHistory(
            id: String,
            days: Int,
        ) = emptyList<com.aliayali.model.market.MarketPricePoint>()

        override suspend fun syncMarketCoins(
            perPage: Int,
            page: Int,
        ): AppResult<Unit> {
            TODO("Not yet implemented")
        }

        override fun observeMarketData(id: String): Flow<Coin?> {
            TODO("Not yet implemented")
        }

        override fun observeMarketData(): Flow<com.aliayali.model.market.MarketData> =
            flowOf()

        override suspend fun syncMarketData(): AppResult<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun searchCoins(query: String): List<Coin> {
            lastSearchQuery = query
            return searchResults
        }
    }

    private class FakeMarketAssetRepository : MarketAssetRepository {

        var assets: List<MarketAsset> = emptyList()
        override fun observeMarketAsset(id: String): Flow<MarketAsset?> {
            TODO("Not yet implemented")
        }

        override fun observeMarketAssets(): Flow<List<MarketAsset>> =
            flowOf(assets)

        override suspend fun syncMarketAssets(): AppResult<Unit> {
            TODO("Not yet implemented")
        }
    }
}