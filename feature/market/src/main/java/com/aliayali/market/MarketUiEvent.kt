package com.aliayali.market

import com.aliayali.market.model.MarketTab

sealed interface MarketEvent {

    data class SelectTab(
        val tab: MarketTab,
    ) : MarketEvent

    data object Refresh : MarketEvent

    data object Retry : MarketEvent
}