package com.aliayali.data.analyzer

import com.aliayali.model.analysis.MarketReason
import com.aliayali.model.analysis.MarketSignal
import com.aliayali.model.analysis.MarketSignalType
import com.aliayali.model.analysis.MarketSnapshot
import com.aliayali.model.market.Coin
import com.aliayali.model.market.MarketAsset
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MarketReasonBuilderTest {

    private lateinit var builder: MarketReasonBuilder

    @Before
    fun setup() {
        builder = MarketReasonBuilder()
    }

    @Test
    fun `build returns empty list when signals are empty`() {
        val result = builder.build(
            snapshot = createSnapshot(),
            signals = emptyList(),
        )

        assertTrue(result.isEmpty())
    }

    @Test
    fun `build returns USD increase reason for positive change`() {
        val result = builder.build(
            snapshot = createSnapshot(usdChange = 2.0),
            signals = listOf(
                signal(
                    type = MarketSignalType.USD,
                    score = 0.5,
                ),
            ),
        )

        assertEquals(
            listOf(MarketReason.USD_INCREASE),
            result,
        )
    }

    @Test
    fun `build returns USD significant increase for strong signal`() {
        val result = builder.build(
            snapshot = createSnapshot(usdChange = 2.0),
            signals = listOf(
                signal(
                    type = MarketSignalType.USD,
                    score = 0.8,
                ),
            ),
        )

        assertEquals(
            listOf(MarketReason.USD_SIGNIFICANT_INCREASE),
            result,
        )
    }

    @Test
    fun `build returns USD decrease reason for negative change`() {
        val result = builder.build(
            snapshot = createSnapshot(usdChange = -2.0),
            signals = listOf(
                signal(
                    type = MarketSignalType.USD,
                    score = 0.5,
                ),
            ),
        )

        assertEquals(
            listOf(MarketReason.USD_DECREASE),
            result,
        )
    }

    @Test
    fun `build returns USD significant decrease for strong signal`() {
        val result = builder.build(
            snapshot = createSnapshot(usdChange = -2.0),
            signals = listOf(
                signal(
                    type = MarketSignalType.USD,
                    score = 0.8,
                ),
            ),
        )

        assertEquals(
            listOf(MarketReason.USD_SIGNIFICANT_DECREASE),
            result,
        )
    }

    @Test
    fun `build ignores USD when change is null`() {
        val result = builder.build(
            snapshot = createSnapshot(usdChange = null),
            signals = listOf(
                signal(
                    type = MarketSignalType.USD,
                    score = 0.8,
                ),
            ),
        )

        assertTrue(result.isEmpty())
    }

    @Test
    fun `build ignores USD when change is zero`() {
        val result = builder.build(
            snapshot = createSnapshot(usdChange = 0.0),
            signals = listOf(
                signal(
                    type = MarketSignalType.USD,
                    score = 0.8,
                ),
            ),
        )

        assertTrue(result.isEmpty())
    }

    @Test
    fun `build returns GOLD 18K increase reason`() {
        val result = builder.build(
            snapshot = createSnapshot(gold18Change = 2.0),
            signals = listOf(
                signal(
                    type = MarketSignalType.GOLD_18K,
                    score = 0.5,
                ),
            ),
        )

        assertEquals(
            listOf(MarketReason.GOLD_18K_INCREASE),
            result,
        )
    }

    @Test
    fun `build returns GOLD 18K significant decrease`() {
        val result = builder.build(
            snapshot = createSnapshot(gold18Change = -2.0),
            signals = listOf(
                signal(
                    type = MarketSignalType.GOLD_18K,
                    score = 0.8,
                ),
            ),
        )

        assertEquals(
            listOf(MarketReason.GOLD_18K_SIGNIFICANT_DECREASE),
            result,
        )
    }

    @Test
    fun `build returns GOLD ounce increase reason`() {
        val result = builder.build(
            snapshot = createSnapshot(goldOunceChange = 2.0),
            signals = listOf(
                signal(
                    type = MarketSignalType.GOLD_OUNCE,
                    score = 0.5,
                ),
            ),
        )

        assertEquals(
            listOf(MarketReason.GOLD_OUNCE_INCREASE),
            result,
        )
    }

    @Test
    fun `build returns GOLD ounce significant decrease`() {
        val result = builder.build(
            snapshot = createSnapshot(goldOunceChange = -2.0),
            signals = listOf(
                signal(
                    type = MarketSignalType.GOLD_OUNCE,
                    score = 0.8,
                ),
            ),
        )

        assertEquals(
            listOf(MarketReason.GOLD_OUNCE_SIGNIFICANT_DECREASE),
            result,
        )
    }

    @Test
    fun `build returns strong positive crypto reason`() {
        val result = builder.build(
            snapshot = createSnapshot(
                coins = listOf(
                    createCoin(5.0),
                    createCoin(3.0),
                    createCoin(2.0),
                ),
            ),
            signals = listOf(
                signal(
                    type = MarketSignalType.CRYPTO,
                    score = 0.8,
                ),
            ),
        )

        assertEquals(
            listOf(MarketReason.CRYPTO_STRONG_POSITIVE),
            result,
        )
    }

    @Test
    fun `build returns moderate positive crypto reason`() {
        val result = builder.build(
            snapshot = createSnapshot(
                coins = listOf(
                    createCoin(5.0),
                    createCoin(3.0),
                    createCoin(-1.0),
                ),
            ),
            signals = listOf(
                signal(
                    type = MarketSignalType.CRYPTO,
                    score = 0.5,
                ),
            ),
        )

        assertEquals(
            listOf(MarketReason.CRYPTO_MODERATE_POSITIVE),
            result,
        )
    }

    @Test
    fun `build returns weak positive crypto reason`() {
        val result = builder.build(
            snapshot = createSnapshot(
                coins = listOf(
                    createCoin(5.0),
                    createCoin(3.0),
                    createCoin(-1.0),
                ),
            ),
            signals = listOf(
                signal(
                    type = MarketSignalType.CRYPTO,
                    score = 0.2,
                ),
            ),
        )

        assertEquals(
            listOf(MarketReason.CRYPTO_WEAK_POSITIVE),
            result,
        )
    }

    @Test
    fun `build returns strong negative crypto reason`() {
        val result = builder.build(
            snapshot = createSnapshot(
                coins = listOf(
                    createCoin(-5.0),
                    createCoin(-3.0),
                    createCoin(-2.0),
                ),
            ),
            signals = listOf(
                signal(
                    type = MarketSignalType.CRYPTO,
                    score = 0.8,
                ),
            ),
        )

        assertEquals(
            listOf(MarketReason.CRYPTO_STRONG_NEGATIVE),
            result,
        )
    }

    @Test
    fun `build returns moderate negative crypto reason`() {
        val result = builder.build(
            snapshot = createSnapshot(
                coins = listOf(
                    createCoin(-5.0),
                    createCoin(-3.0),
                    createCoin(1.0),
                ),
            ),
            signals = listOf(
                signal(
                    type = MarketSignalType.CRYPTO,
                    score = 0.5,
                ),
            ),
        )

        assertEquals(
            listOf(MarketReason.CRYPTO_MODERATE_NEGATIVE),
            result,
        )
    }

    @Test
    fun `build returns weak negative crypto reason`() {
        val result = builder.build(
            snapshot = createSnapshot(
                coins = listOf(
                    createCoin(-5.0),
                    createCoin(-3.0),
                    createCoin(1.0),
                ),
            ),
            signals = listOf(
                signal(
                    type = MarketSignalType.CRYPTO,
                    score = 0.2,
                ),
            ),
        )

        assertEquals(
            listOf(MarketReason.CRYPTO_WEAK_NEGATIVE),
            result,
        )
    }

    @Test
    fun `build returns mixed crypto reason`() {
        val result = builder.build(
            snapshot = createSnapshot(
                coins = listOf(
                    createCoin(5.0),
                    createCoin(-3.0),
                    createCoin(0.0),
                ),
            ),
            signals = listOf(
                signal(
                    type = MarketSignalType.CRYPTO,
                    score = 0.8,
                ),
            ),
        )

        assertEquals(
            listOf(MarketReason.CRYPTO_MIXED),
            result,
        )
    }

    @Test
    fun `build ignores crypto when all changes are null`() {
        val result = builder.build(
            snapshot = createSnapshot(
                coins = listOf(
                    createCoin(null),
                    createCoin(null),
                ),
            ),
            signals = listOf(
                signal(
                    type = MarketSignalType.CRYPTO,
                    score = 0.8,
                ),
            ),
        )

        assertTrue(result.isEmpty())
    }

    @Test
    fun `build returns strong positive local market reason`() {
        val result = builder.build(
            snapshot = createSnapshot(
                marketAssets = listOf(
                    createMarketAsset(2.0),
                ),
            ),
            signals = listOf(
                signal(
                    type = MarketSignalType.LOCAL_MARKET,
                    score = 0.8,
                ),
            ),
        )

        assertEquals(
            listOf(MarketReason.LOCAL_STRONG_POSITIVE),
            result,
        )
    }

    @Test
    fun `build returns strong negative local market reason`() {
        val result = builder.build(
            snapshot = createSnapshot(
                marketAssets = listOf(
                    createMarketAsset(-2.0),
                ),
            ),
            signals = listOf(
                signal(
                    type = MarketSignalType.LOCAL_MARKET,
                    score = -0.8,
                ),
            ),
        )

        assertEquals(
            listOf(MarketReason.LOCAL_STRONG_NEGATIVE),
            result,
        )
    }

    @Test
    fun `build returns moderate positive local market reason`() {
        val result = builder.build(
            snapshot = createSnapshot(
                marketAssets = listOf(
                    createMarketAsset(2.0),
                ),
            ),
            signals = listOf(
                signal(
                    type = MarketSignalType.LOCAL_MARKET,
                    score = 0.5,
                ),
            ),
        )

        assertEquals(
            listOf(MarketReason.LOCAL_MODERATE_POSITIVE),
            result,
        )
    }

    @Test
    fun `build returns moderate negative local market reason`() {
        val result = builder.build(
            snapshot = createSnapshot(
                marketAssets = listOf(
                    createMarketAsset(-2.0),
                ),
            ),
            signals = listOf(
                signal(
                    type = MarketSignalType.LOCAL_MARKET,
                    score = -0.5,
                ),
            ),
        )

        assertEquals(
            listOf(MarketReason.LOCAL_MODERATE_NEGATIVE),
            result,
        )
    }

    @Test
    fun `build returns weak local market reason for positive signal`() {
        val result = builder.build(
            snapshot = createSnapshot(),
            signals = listOf(
                signal(
                    type = MarketSignalType.LOCAL_MARKET,
                    score = 0.2,
                ),
            ),
        )

        assertEquals(
            listOf(MarketReason.LOCAL_WEAK),
            result,
        )
    }

    @Test
    fun `build returns weak local market reason for negative signal`() {
        val result = builder.build(
            snapshot = createSnapshot(),
            signals = listOf(
                signal(
                    type = MarketSignalType.LOCAL_MARKET,
                    score = -0.2,
                ),
            ),
        )

        assertEquals(
            listOf(MarketReason.LOCAL_WEAK),
            result,
        )
    }

    @Test
    fun `build sorts reasons by signal impact`() {
        val result = builder.build(
            snapshot = createSnapshot(
                usdChange = 2.0,
                gold18Change = 2.0,
            ),
            signals = listOf(
                signal(
                    type = MarketSignalType.USD,
                    score = 0.2,
                    weight = 1.0,
                ),
                signal(
                    type = MarketSignalType.GOLD_18K,
                    score = 0.8,
                    weight = 1.0,
                ),
            ),
        )

        assertEquals(
            listOf(
                MarketReason.GOLD_18K_SIGNIFICANT_INCREASE,
                MarketReason.USD_INCREASE,
            ),
            result,
        )
    }

    @Test
    fun `build returns at most three reasons`() {
        val result = builder.build(
            snapshot = createSnapshot(
                usdChange = 2.0,
                gold18Change = 2.0,
                goldOunceChange = 2.0,
            ),
            signals = listOf(
                signal(
                    type = MarketSignalType.USD,
                    score = 0.8,
                    weight = 1.0,
                ),
                signal(
                    type = MarketSignalType.GOLD_18K,
                    score = 0.7,
                    weight = 1.0,
                ),
                signal(
                    type = MarketSignalType.GOLD_OUNCE,
                    score = 0.6,
                    weight = 1.0,
                ),
                signal(
                    type = MarketSignalType.LOCAL_MARKET,
                    score = 0.5,
                    weight = 1.0,
                ),
            ),
        )

        assertEquals(3, result.size)
    }

    private fun signal(
        type: MarketSignalType,
        score: Double,
        weight: Double = 1.0,
    ): MarketSignal {
        return MarketSignal(
            type = type,
            score = score,
            weight = weight,
        )
    }

    private fun createSnapshot(
        usdChange: Double? = 1.0,
        gold18Change: Double? = 1.0,
        goldOunceChange: Double? = 1.0,
        coins: List<Coin> = emptyList(),
        marketAssets: List<MarketAsset> = emptyList(),
    ): MarketSnapshot {
        return MarketSnapshot(
            usd = createMarketAsset(change = usdChange),
            gold18 = createMarketAsset(change = gold18Change),
            goldOunce = createMarketAsset(change = goldOunceChange),
            coins = coins,
            marketAssets = marketAssets,
        )
    }

    private fun createCoin(
        change: Double?,
    ): Coin {
        return Coin(
            id = "test-coin",
            symbol = "TEST",
            name = "Test Coin",
            imageUrl = "",
            price = 100.0,
            changePercent24h = change,
        )
    }

    private fun createMarketAsset(
        change: Double?,
    ): MarketAsset {
        return MarketAsset(
            id = "test-asset",
            symbol = "TEST",
            name = "Test Asset",
            price = 100.0,
            changePercent = change,
            unit = "USD",
        )
    }
}