package com.aliayali.home.mapper

import com.aliayali.home.R
import com.aliayali.home.model.MarketStatus
import com.aliayali.model.analysis.MarketAnalysis
import com.aliayali.model.analysis.MarketCondition
import com.aliayali.model.analysis.MarketOverview
import com.aliayali.model.analysis.MarketReason
import com.aliayali.model.analysis.MarketSignal
import com.aliayali.model.analysis.MarketSignalType
import com.aliayali.model.market.MarketAsset
import org.junit.Assert.assertEquals
import org.junit.Test

class MarketOverviewMapperTest {

    @Test
    fun `asUiModel maps market condition to ui status`() {
        val overview = createOverview(
            condition = MarketCondition.Critical,
        )

        val result = overview.asUiModel()

        assertEquals(
            MarketStatus.Critical,
            result.marketStatus,
        )
    }

    @Test
    fun `asUiModel maps condition to correct title`() {
        val overview = createOverview(
            condition = MarketCondition.Calm,
        )

        val result = overview.asUiModel()

        assertEquals(
            R.string.feature_home_market_status_calm,
            result.insightTitleRes,
        )
    }

    @Test
    fun `asUiModel uses high confidence description`() {
        val overview = createOverview(
            condition = MarketCondition.Normal,
            confidence = 0.80,
        )

        val result = overview.asUiModel()

        assertEquals(
            R.string.feature_home_market_description_normal_high_confidence,
            result.insightDescriptionRes,
        )
    }

    @Test
    fun `asUiModel uses medium confidence description`() {
        val overview = createOverview(
            condition = MarketCondition.Volatile,
            confidence = 0.60,
        )

        val result = overview.asUiModel()

        assertEquals(
            R.string.feature_home_market_description_volatile_medium_confidence,
            result.insightDescriptionRes,
        )
    }

    @Test
    fun `asUiModel uses low confidence description`() {
        val overview = createOverview(
            condition = MarketCondition.Critical,
            confidence = 0.30,
        )

        val result = overview.asUiModel()

        assertEquals(
            R.string.feature_home_market_description_low_confidence,
            result.insightDescriptionRes,
        )
    }

    @Test
    fun `asUiModel maps reasons`() {
        val reasons = listOf(
            MarketReason.USD_INCREASE,
            MarketReason.GOLD_18K_DECREASE,
        )

        val overview = createOverview(
            reasons = reasons,
        )

        val result = overview.asUiModel()

        assertEquals(reasons, result.reasons)
    }

    @Test
    fun `asUiModel maps market signals`() {
        val signals = listOf(
            MarketSignal(
                type = MarketSignalType.USD,
                score = 0.8,
                weight = 0.15,
            ),
            MarketSignal(
                type = MarketSignalType.CRYPTO,
                score = -0.4,
                weight = 0.30,
            ),
        )

        val overview = createOverview(
            signals = signals,
        )

        val result = overview.asUiModel()

        assertEquals(2, result.signals.size)

        assertEquals(
            R.string.feature_home_market_signal_usd,
            result.signals[0].titleRes,
        )
        assertEquals(0.8, result.signals[0].score, 0.0)
        assertEquals(0.15, result.signals[0].weight, 0.0)

        assertEquals(
            R.string.feature_home_market_signal_crypto,
            result.signals[1].titleRes,
        )
        assertEquals(-0.4, result.signals[1].score, 0.0)
        assertEquals(0.30, result.signals[1].weight, 0.0)
    }

    @Test
    fun `asUiModel maps usd and gold assets`() {
        val usd = createAsset(
            id = "usd",
            symbol = "USD",
            name = "Dollar",
        )

        val gold18 = createAsset(
            id = "gold18",
            symbol = "IR_GOLD_18K",
            name = "Gold 18K",
        )

        val overview = createOverview(
            usd = usd,
            gold18 = gold18,
        )

        val result = overview.asUiModel()

        assertEquals(usd.id, result.usd.id)
        assertEquals(usd.symbol, result.usd.symbol)

        assertEquals(gold18.id, result.gold18.id)
        assertEquals(gold18.symbol, result.gold18.symbol)
    }

    private fun createOverview(
        condition: MarketCondition = MarketCondition.Calm,
        confidence: Double = 0.80,
        reasons: List<MarketReason> = emptyList(),
        signals: List<MarketSignal> = emptyList(),
        usd: MarketAsset = createAsset(
            id = "usd",
            symbol = "USD",
            name = "Dollar",
        ),
        gold18: MarketAsset = createAsset(
            id = "gold18",
            symbol = "IR_GOLD_18K",
            name = "Gold 18K",
        ),
    ) = MarketOverview(
        analysis = MarketAnalysis(
            condition = condition,
            score = 0.5,
            confidence = confidence,
            signals = signals,
            reasons = reasons,
        ),
        usd = usd,
        gold18 = gold18,
    )

    private fun createAsset(
        id: String,
        symbol: String,
        name: String,
    ) = MarketAsset(
        id = id,
        symbol = symbol,
        name = name,
        price = 100.0,
        changePercent = 2.0,
        unit = "IRT",
    )
}