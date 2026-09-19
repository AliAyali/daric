package com.aliayali.home.mapper

import com.aliayali.home.model.MarketStatus
import com.aliayali.model.analysis.MarketCondition

fun MarketCondition.asUiModel(): MarketStatus =
    when (this) {
        MarketCondition.Calm -> MarketStatus.Calm
        MarketCondition.Normal -> MarketStatus.Normal
        MarketCondition.Volatile -> MarketStatus.Volatile
        MarketCondition.Critical -> MarketStatus.Critical
    }