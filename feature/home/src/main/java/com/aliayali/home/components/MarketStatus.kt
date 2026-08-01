package com.aliayali.home.components

import androidx.annotation.StringRes
import com.aliayali.home.R

enum class MarketStatus(
    val progress: Float,
    @param:StringRes val titleRes: Int,
    @param:StringRes val descriptionRes: Int,
    val tone: MarketStatusTone,
) {

    Calm(
        progress = .25f,
        titleRes = R.string.feature_home_market_status_calm,
        descriptionRes = R.string.feature_home_market_status_calm_description,
        tone = MarketStatusTone.Success,
    ),

    Normal(
        progress = .50f,
        titleRes = R.string.feature_home_market_status_normal,
        descriptionRes = R.string.feature_home_market_status_normal_description,
        tone = MarketStatusTone.Normal,
    ),

    Volatile(
        progress = .80f,
        titleRes = R.string.feature_home_market_status_volatile,
        descriptionRes = R.string.feature_home_market_status_volatile_description,
        tone = MarketStatusTone.Warning,
    ),

    Critical(
        progress = 1f,
        titleRes = R.string.feature_home_market_status_critical,
        descriptionRes = R.string.feature_home_market_status_critical_description,
        tone = MarketStatusTone.Error,
    ),
}

enum class MarketStatusTone {
    Success,
    Normal,
    Warning,
    Error,
}