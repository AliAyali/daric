package com.aliayali.market.mapper

import com.aliayali.designsystem.icon.DaricMarketIcons

fun String.toMarketIcon(): Int {
    return when (uppercase()) {

        // Gold
        "IR_GOLD_18K",
        "IR_GOLD_24K",
        "IR_GOLD_MELTED",
        "XAUUSD",
            -> DaricMarketIcons.GOLD

        // Coins
        "IR_COIN_1G" -> DaricMarketIcons.COIN_1G
        "IR_COIN_QUARTER" -> DaricMarketIcons.COIN_QUARTER
        "IR_COIN_HALF" -> DaricMarketIcons.COIN_HALF
        "IR_COIN_EMAMI" -> DaricMarketIcons.COIN_EMAMI
        "IR_COIN_BAHAR" -> DaricMarketIcons.COIN_BAHAR

        // Currency
        "USD" -> DaricMarketIcons.USD
        "EUR" -> DaricMarketIcons.EUR
        "AED" -> DaricMarketIcons.AED
        "GBP" -> DaricMarketIcons.GBP
        "JPY" -> DaricMarketIcons.JPY
        "KWD" -> DaricMarketIcons.KWD
        "AUD" -> DaricMarketIcons.AUD
        "CAD" -> DaricMarketIcons.CAD
        "CNY" -> DaricMarketIcons.CNY
        "TRY" -> DaricMarketIcons.TRY
        "SAR" -> DaricMarketIcons.SAR
        "CHF" -> DaricMarketIcons.CHF
        "INR" -> DaricMarketIcons.INR
        "PKR" -> DaricMarketIcons.PKR
        "IQD" -> DaricMarketIcons.IQD
        "SYP" -> DaricMarketIcons.SYP
        "SEK" -> DaricMarketIcons.SEK
        "QAR" -> DaricMarketIcons.QAR
        "OMR" -> DaricMarketIcons.OMR
        "BHD" -> DaricMarketIcons.BHD
        "AFN" -> DaricMarketIcons.AFN
        "MYR" -> DaricMarketIcons.MYR
        "THB" -> DaricMarketIcons.THB
        "RUB" -> DaricMarketIcons.RUB
        "AZN" -> DaricMarketIcons.AZN
        "AMD" -> DaricMarketIcons.AMD
        "GEL" -> DaricMarketIcons.GEL

        // Fallback
        else -> DaricMarketIcons.DEFAULT
    }
}