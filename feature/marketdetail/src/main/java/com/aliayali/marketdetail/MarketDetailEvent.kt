package com.aliayali.marketdetail

sealed interface MarketDetailEvent {
    data object Refresh : MarketDetailEvent
}