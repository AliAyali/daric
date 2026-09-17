package com.aliayali.domain

import com.aliayali.model.analysis.MarketAnalysis
import com.aliayali.model.analysis.MarketCondition
import com.aliayali.model.market.Coin
import com.aliayali.model.market.MarketAsset
import com.aliayali.model.market.MarketData
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetMarketOverviewUseCaseTest {

    private lateinit var useCase: GetMarketOverviewUseCase

    private val analyzer = object : MarketAnalyzer {
        override fun analyze(
            snapshot: com.aliayali.model.analysis.MarketSnapshot,
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

    @Before
    fun setup() {
        useCase = GetMarketOverviewUseCase(analyzer)
    }

    @Test
    fun `invoke creates overview using required market assets`() {
        val usd = createAsset(
            id = "usd",
            symbol = "USD",
            name = "Dollar",
            price = 100.0,
        )

        val gold18 = createAsset(
            id = "gold18",
            symbol = "IR_GOLD_18K",
            name = "Gold 18K",
            price = 10_000_000.0,
        )

        val goldOunce = createAsset(
            id = "gold-ounce",
            symbol = "XAUUSD",
            name = "Gold Ounce",
            price = 3_000.0,
        )

        val marketAssets = listOf(
            usd,
            gold18,
            goldOunce,
        )

        val marketData = MarketData(
            coins = listOf(
                createCoin(),
            ),
        )

        val result = useCase(
            marketData = marketData,
            marketAssets = marketAssets,
        )

        assertEquals(usd, result.usd)
        assertEquals(gold18, result.gold18)
        assertEquals(MarketCondition.Calm, result.analysis.condition)
        assertEquals(0.5, result.analysis.score, 0.0)
        assertEquals(0.8, result.analysis.confidence, 0.0)
    }

    @Test
    fun `invoke passes all market data and assets to analyzer`() {
        var receivedSnapshot: com.aliayali.model.analysis.MarketSnapshot? = null

        val analyzer = object : MarketAnalyzer {
            override fun analyze(
                snapshot: com.aliayali.model.analysis.MarketSnapshot,
            ): MarketAnalysis {
                receivedSnapshot = snapshot

                return MarketAnalysis(
                    condition = MarketCondition.Calm,
                    score = 0.0,
                    confidence = 0.0,
                    signals = emptyList(),
                    reasons = emptyList(),
                )
            }
        }

        val useCase = GetMarketOverviewUseCase(analyzer)

        val usd = createAsset(symbol = "USD")
        val gold18 = createAsset(symbol = "IR_GOLD_18K")
        val goldOunce = createAsset(symbol = "XAUUSD")

        val extraAsset = createAsset(
            id = "extra",
            symbol = "EUR",
        )

        val coin = createCoin(id = "bitcoin")

        val marketAssets = listOf(
            usd,
            gold18,
            goldOunce,
            extraAsset,
        )

        val marketData = MarketData(
            coins = listOf(coin),
        )

        useCase(
            marketData = marketData,
            marketAssets = marketAssets,
        )

        assertEquals(usd, receivedSnapshot?.usd)
        assertEquals(gold18, receivedSnapshot?.gold18)
        assertEquals(goldOunce, receivedSnapshot?.goldOunce)
        assertEquals(listOf(coin), receivedSnapshot?.coins)
        assertEquals(marketAssets, receivedSnapshot?.marketAssets)
    }

    private fun createAsset(
        id: String = "asset-id",
        symbol: String = "USD",
        name: String = "Asset",
        price: Double? = 100.0,
    ) = MarketAsset(
        id = id,
        symbol = symbol,
        name = name,
        price = price,
        changePercent = 1.0,
        unit = "IRT",
    )

    private fun createCoin(
        id: String = "coin-id",
    ) = Coin(
        id = id,
        symbol = "BTC",
        name = "Bitcoin",
        imageUrl = "image",
        price = 100.0,
        changePercent24h = 2.0,
    )
}