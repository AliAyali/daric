package com.aliayali.data.analyzer

import com.aliayali.model.analysis.MarketSignalType
import com.aliayali.model.analysis.MarketSnapshot
import com.aliayali.model.market.Coin
import com.aliayali.model.market.MarketAsset
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MarketScoreCalculatorTest {

    private lateinit var calculator: MarketScoreCalculator

    @Before
    fun setup() {
        calculator = MarketScoreCalculator()
    }

    @Test
    fun `createSignals returns five signals`() {
        val snapshot = createSnapshot()

        val signals = calculator.createSignals(snapshot)

        assertEquals(5, signals.size)
    }

    @Test
    fun `createSignals returns signals with expected types`() {
        val snapshot = createSnapshot()

        val signals = calculator.createSignals(snapshot)

        assertEquals(
            listOf(
                MarketSignalType.USD,
                MarketSignalType.GOLD_18K,
                MarketSignalType.GOLD_OUNCE,
                MarketSignalType.CRYPTO,
                MarketSignalType.LOCAL_MARKET,
            ),
            signals.map { it.type },
        )
    }

    @Test
    fun `createSignals converts asset changes into normalized scores`() {
        val snapshot = createSnapshot(
            usdChange = 5.0,
            gold18Change = -2.5,
            goldOunceChange = 10.0,
        )

        val signals = calculator.createSignals(snapshot)

        assertEquals(1.0, signals[0].score, 0.0001)
        assertEquals(-0.5, signals[1].score, 0.0001)
        assertEquals(1.0, signals[2].score, 0.0001)
    }

    @Test
    fun `createSignals returns zero score for null changes`() {
        val snapshot = createSnapshot(
            usdChange = null,
            gold18Change = null,
            goldOunceChange = null,
        )

        val signals = calculator.createSignals(snapshot)

        assertEquals(0.0, signals[0].score, 0.0001)
        assertEquals(0.0, signals[1].score, 0.0001)
        assertEquals(0.0, signals[2].score, 0.0001)
    }

    @Test
    fun `createSignals clamps asset score to one`() {
        val snapshot = createSnapshot(
            usdChange = 10.0,
            gold18Change = 100.0,
            goldOunceChange = 7.0,
        )

        val signals = calculator.createSignals(snapshot)

        assertEquals(1.0, signals[0].score, 0.0001)
        assertEquals(1.0, signals[1].score, 0.0001)
        assertEquals(1.0, signals[2].score, 0.0001)
    }

    @Test
    fun `createSignals clamps asset score to negative one`() {
        val snapshot = createSnapshot(
            usdChange = -10.0,
            gold18Change = -100.0,
            goldOunceChange = -7.0,
        )

        val signals = calculator.createSignals(snapshot)

        assertEquals(-1.0, signals[0].score, 0.0001)
        assertEquals(-1.0, signals[1].score, 0.0001)
        assertEquals(-1.0, signals[2].score, 0.0001)
    }

    @Test
    fun `createSignals returns zero crypto score when there are no coins`() {
        val snapshot = createSnapshot(
            coins = emptyList(),
        )

        val signals = calculator.createSignals(snapshot)

        assertEquals(0.0, signals[3].score, 0.0001)
    }

    @Test
    fun `createSignals calculates positive crypto score`() {
        val snapshot = createSnapshot(
            coins = listOf(
                createCoin(change = 5.0),
                createCoin(change = 3.0),
                createCoin(change = 2.0),
            ),
        )

        val signals = calculator.createSignals(snapshot)

        // breadth = 1.0
        // average change = 10 / 3
        // average score = (10 / 3) / 5 = 0.6667
        //
        // crypto score =
        // 1.0 * 0.6 + 0.6667 * 0.4
        // ≈ 0.8667

        assertEquals(0.8667, signals[3].score, 0.0001)
    }

    @Test
    fun `createSignals ignores null crypto changes`() {
        val snapshot = createSnapshot(
            coins = listOf(
                createCoin(change = 5.0),
                createCoin(change = null),
                createCoin(change = 3.0),
            ),
        )

        val signals = calculator.createSignals(snapshot)

        // Only +5 and +3 are considered.
        // breadth = 1
        // average = 4
        // averageScore = 0.8
        // score = 1 * 0.6 + 0.8 * 0.4 = 0.92

        assertEquals(0.92, signals[3].score, 0.0001)
    }

    @Test
    fun `createSignals calculates negative crypto score`() {
        val snapshot = createSnapshot(
            coins = listOf(
                createCoin(change = -5.0),
                createCoin(change = -3.0),
                createCoin(change = -2.0),
            ),
        )

        val signals = calculator.createSignals(snapshot)

        // breadth = -1
        // average change = -10 / 3
        // average score ≈ -0.6667
        //
        // score ≈ -0.6 + (-0.6667 * 0.4)
        // ≈ -0.8667

        assertEquals(-0.8667, signals[3].score, 0.0001)
    }

    @Test
    fun `createSignals returns zero local market score when there are no assets`() {
        val snapshot = createSnapshot(
            marketAssets = emptyList(),
        )

        val signals = calculator.createSignals(snapshot)

        assertEquals(0.0, signals[4].score, 0.0001)
    }

    @Test
    fun `createSignals ignores null local market changes`() {
        val snapshot = createSnapshot(
            marketAssets = listOf(
                createMarketAsset(change = 5.0),
                createMarketAsset(change = null),
                createMarketAsset(change = 3.0),
            ),
        )

        val signals = calculator.createSignals(snapshot)

        // Same calculation as crypto:
        // breadth = 1
        // average = 4
        // averageScore = 0.8
        // score = 0.6 + 0.32 = 0.92

        assertEquals(0.92, signals[4].score, 0.0001)
    }

    @Test
    fun `calculateScore returns weighted sum of signals`() {
        val snapshot = createSnapshot(
            usdChange = 5.0,
            gold18Change = 5.0,
            goldOunceChange = 5.0,
            coins = emptyList(),
            marketAssets = emptyList(),
        )

        val signals = calculator.createSignals(snapshot)

        val result = calculator.calculateScore(signals)

        // USD      = 1.0 * 0.15 = 0.15
        // GOLD 18K = 1.0 * 0.15 = 0.15
        // OUNCE    = 1.0 * 0.10 = 0.10
        // CRYPTO   = 0.0 * 0.30 = 0.00
        // LOCAL    = 0.0 * 0.15 = 0.00
        //
        // total = 0.40

        assertEquals(0.40, result, 0.0001)
    }

    @Test
    fun `calculateScore returns zero for empty signals`() {
        val result = calculator.calculateScore(emptyList())

        assertEquals(0.0, result, 0.0)
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
            id = "bitcoin",
            symbol = "BTC",
            name = "Bitcoin",
            imageUrl = "",
            price = 100.0,
            changePercent24h = change,
        )
    }

    private fun createMarketAsset(
        change: Double?,
    ): MarketAsset {
        return MarketAsset(
            id = "test",
            symbol = "TEST",
            name = "Test Asset",
            price = 100.0,
            changePercent = change,
            unit = "USD",
        )
    }
}