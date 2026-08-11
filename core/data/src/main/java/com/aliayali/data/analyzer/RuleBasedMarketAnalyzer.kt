package com.aliayali.data.analyzer

import com.aliayali.domain.MarketAnalyzer
import com.aliayali.model.analysis.MarketAnalysis
import com.aliayali.model.analysis.MarketSnapshot
import com.aliayali.model.analysis.MarketStatus
import javax.inject.Inject

class RuleBasedMarketAnalyzer @Inject constructor() : MarketAnalyzer {

    override fun analyze(snapshot: MarketSnapshot): MarketAnalysis {

        val score =
            calculateScore(snapshot)

        val status = when {
            score >= 0.5 -> MarketStatus.Bullish
            score <= -0.5 -> MarketStatus.Bearish
            else -> MarketStatus.Volatile
        }

        return MarketAnalysis(
            status = status,
            score = score,
            title = createTitle(status),
            description = createDescription(
                status = status,
                score = score,
            ),
        )
    }

    private fun calculateScore(
        snapshot: MarketSnapshot,
    ): Double {

        val usd = snapshot.usd.changePercent ?: 0.0
        val gold18 = snapshot.gold18.changePercent ?: 0.0
        val goldOunce = snapshot.goldOunce.changePercent ?: 0.0
        val btc = snapshot.btc.changePercent24h ?: 0.0

        return (usd * 0.30 + gold18 * 0.30 + goldOunce * 0.20 + btc * 0.20)
    }

    private fun createTitle(
        status: MarketStatus,
    ): String =
        when (status) {
            MarketStatus.Bullish ->
                "بازار تمایل صعودی دارد"

            MarketStatus.Bearish ->
                "بازار تمایل نزولی دارد"

            MarketStatus.Volatile ->
                "بازار در وضعیت نوسانی است"
        }

    private fun createDescription(
        status: MarketStatus,
        score: Double,
    ): String =
        when (status) {
            MarketStatus.Bullish ->
                "بررسی شاخص‌های اصلی نشان‌دهنده فشار صعودی در بازار است."

            MarketStatus.Bearish ->
                "بررسی شاخص‌های اصلی نشان‌دهنده فشار نزولی در بازار است."

            MarketStatus.Volatile ->
                "شاخص‌های اصلی جهت مشخصی را نشان نمی‌دهند و بازار نوسانی است."
        }
}