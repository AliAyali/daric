package com.aliayali.home.model

import androidx.annotation.StringRes
import com.aliayali.model.analysis.MarketReason

data class MarketOverviewCardUiModel(
    val marketStatus: MarketStatus,
    @param:StringRes val insightTitleRes: Int,
    @param:StringRes val insightDescriptionRes: Int,
    val reasons: List<MarketReason>,
    val usd: MarketAssetUiModel,
    val gold18: MarketAssetUiModel,
    val signals: List<MarketSignalUiModel>,
)