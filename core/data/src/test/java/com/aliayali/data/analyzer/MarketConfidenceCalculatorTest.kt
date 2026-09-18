package com.aliayali.data.analyzer

import com.aliayali.model.analysis.MarketSignal
import com.aliayali.model.analysis.MarketSignalType
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MarketConfidenceCalculatorTest {

    private lateinit var calculator: MarketConfidenceCalculator

    private val signalType = MarketSignalType.entries.first()

    @Before
    fun setup() {
        calculator = MarketConfidenceCalculator()
    }

    @Test
    fun `calculate returns zero when signals are empty`() {
        val result = calculator.calculate(emptyList())

        assertEquals(0.0, result, 0.0)
    }

    @Test
    fun `calculate returns zero when total weight is zero`() {
        val signals = listOf(
            MarketSignal(
                type = signalType,
                score = 0.8,
                weight = 0.0,
            ),
            MarketSignal(
                type = signalType,
                score = -0.5,
                weight = 0.0,
            ),
        )

        val result = calculator.calculate(signals)

        assertEquals(0.0, result, 0.0)
    }

    @Test
    fun `calculate returns expected confidence for a single positive signal`() {
        val signals = listOf(
            MarketSignal(
                type = signalType,
                score = 0.8,
                weight = 1.0,
            ),
        )

        val result = calculator.calculate(signals)

        // strength = 0.8
        // agreement = 1.0
        // confidence = 0.8 * 0.6 + 1.0 * 0.4 = 0.88
        assertEquals(0.88, result, 0.0001)
    }

    @Test
    fun `calculate returns expected confidence for a single negative signal`() {
        val signals = listOf(
            MarketSignal(
                type = signalType,
                score = -0.8,
                weight = 1.0,
            ),
        )

        val result = calculator.calculate(signals)

        // abs(score) = 0.8
        // agreement = 1.0
        // confidence = 0.8 * 0.6 + 1.0 * 0.4 = 0.88
        assertEquals(0.88, result, 0.0001)
    }

    @Test
    fun `calculate returns lower confidence when signals disagree`() {
        val signals = listOf(
            MarketSignal(
                type = signalType,
                score = 1.0,
                weight = 1.0,
            ),
            MarketSignal(
                type = signalType,
                score = -1.0,
                weight = 1.0,
            ),
        )

        val result = calculator.calculate(signals)

        // strength = 1.0
        // agreement = 0.0
        // confidence = 1.0 * 0.6 = 0.6
        assertEquals(0.6, result, 0.0001)
    }

    @Test
    fun `calculate returns expected confidence for mixed signals`() {
        val signals = listOf(
            MarketSignal(
                type = signalType,
                score = 0.8,
                weight = 2.0,
            ),
            MarketSignal(
                type = signalType,
                score = 0.4,
                weight = 1.0,
            ),
            MarketSignal(
                type = signalType,
                score = -0.2,
                weight = 1.0,
            ),
        )

        val result = calculator.calculate(signals)

        // totalWeight = 4
        //
        // strength =
        // (abs(0.8) * 2 + abs(0.4) * 1 + abs(-0.2) * 1) / 4
        // = 2.2 / 4
        // = 0.55
        //
        // positiveWeight = 3
        // negativeWeight = 1
        //
        // agreement = abs(3 - 1) / 4
        // = 0.5
        //
        // confidence =
        // 0.55 * 0.6 + 0.5 * 0.4
        // = 0.53

        assertEquals(0.53, result, 0.0001)
    }

    @Test
    fun `calculate ignores signal direction when calculating strength`() {
        val signals = listOf(
            MarketSignal(
                type = signalType,
                score = 0.5,
                weight = 1.0,
            ),
            MarketSignal(
                type = signalType,
                score = -0.5,
                weight = 1.0,
            ),
        )

        val result = calculator.calculate(signals)

        // strength = 0.5
        // agreement = 0.0
        // confidence = 0.5 * 0.6 = 0.3
        assertEquals(0.3, result, 0.0001)
    }

    @Test
    fun `calculate returns zero when all scores are zero`() {
        val signals = listOf(
            MarketSignal(
                type = signalType,
                score = 0.0,
                weight = 1.0,
            ),
            MarketSignal(
                type = signalType,
                score = 0.0,
                weight = 2.0,
            ),
        )

        val result = calculator.calculate(signals)

        assertEquals(0.0, result, 0.0)
    }
}