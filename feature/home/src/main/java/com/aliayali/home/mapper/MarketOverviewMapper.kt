package com.aliayali.home.mapper

import androidx.annotation.StringRes
import com.aliayali.home.R
import com.aliayali.home.model.MarketOverviewCardUiModel
import com.aliayali.home.model.MarketSignalUiModel
import com.aliayali.model.analysis.MarketCondition
import com.aliayali.model.analysis.MarketOverview
import com.aliayali.model.analysis.MarketSignalType

fun MarketOverview.asUiModel(): MarketOverviewCardUiModel =
    MarketOverviewCardUiModel(
        marketStatus = analysis.condition.asUiModel(),

        insightTitleRes = analysis.condition.titleRes(),

        insightDescriptionRes = analysis.condition.descriptionRes(
            confidence = analysis.confidence,
        ),

        reasons = analysis.reasons,

        usd = usd.asUiModel(),
        gold18 = gold18.asUiModel(),

        signals = analysis.signals.map {
            MarketSignalUiModel(
                titleRes = it.type.asUiTitleRes(),
                score = it.score,
                weight = it.weight,
            )
        },
    )

@StringRes
private fun MarketCondition.titleRes(): Int =
    when (this) {
        MarketCondition.Calm ->
            R.string.feature_home_market_status_calm

        MarketCondition.Normal ->
            R.string.feature_home_market_status_normal

        MarketCondition.Volatile ->
            R.string.feature_home_market_status_volatile

        MarketCondition.Critical ->
            R.string.feature_home_market_status_critical
    }

@StringRes
private fun MarketCondition.descriptionRes(
    confidence: Double,
): Int =
    when {
        confidence >= 0.75 -> when (this) {
            MarketCondition.Calm ->
                R.string.feature_home_market_description_calm_high_confidence

            MarketCondition.Normal ->
                R.string.feature_home_market_description_normal_high_confidence

            MarketCondition.Volatile ->
                R.string.feature_home_market_description_volatile_high_confidence

            MarketCondition.Critical ->
                R.string.feature_home_market_description_critical_high_confidence
        }

        confidence >= 0.45 -> when (this) {
            MarketCondition.Calm ->
                R.string.feature_home_market_description_calm_medium_confidence

            MarketCondition.Normal ->
                R.string.feature_home_market_description_normal_medium_confidence

            MarketCondition.Volatile ->
                R.string.feature_home_market_description_volatile_medium_confidence

            MarketCondition.Critical ->
                R.string.feature_home_market_description_critical_medium_confidence
        }

        else ->
            R.string.feature_home_market_description_low_confidence
    }

@StringRes
private fun MarketSignalType.asUiTitleRes(): Int =
    when (this) {
        MarketSignalType.USD ->
            R.string.feature_home_market_signal_usd

        MarketSignalType.GOLD_18K ->
            R.string.feature_home_market_signal_gold_18k

        MarketSignalType.GOLD_OUNCE ->
            R.string.feature_home_market_signal_gold_ounce

        MarketSignalType.CRYPTO ->
            R.string.feature_home_market_signal_crypto

        MarketSignalType.LOCAL_MARKET ->
            R.string.feature_home_market_signal_local_market
    }