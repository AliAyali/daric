package com.aliayali.home.mapper

import androidx.annotation.StringRes
import com.aliayali.home.R
import com.aliayali.model.analysis.MarketReason

@StringRes
fun MarketReason.asStringRes(): Int =
    when (this) {
        MarketReason.USD_SIGNIFICANT_INCREASE ->
            R.string.feature_home_market_reason_usd_significant_increase

        MarketReason.USD_INCREASE ->
            R.string.feature_home_market_reason_usd_increase

        MarketReason.USD_SIGNIFICANT_DECREASE ->
            R.string.feature_home_market_reason_usd_significant_decrease

        MarketReason.USD_DECREASE ->
            R.string.feature_home_market_reason_usd_decrease

        MarketReason.USD_MINOR_CHANGE ->
            R.string.feature_home_market_reason_usd_minor_change

        MarketReason.GOLD_18K_SIGNIFICANT_INCREASE ->
            R.string.feature_home_market_reason_gold_18k_significant_increase

        MarketReason.GOLD_18K_INCREASE ->
            R.string.feature_home_market_reason_gold_18k_increase

        MarketReason.GOLD_18K_SIGNIFICANT_DECREASE ->
            R.string.feature_home_market_reason_gold_18k_significant_decrease

        MarketReason.GOLD_18K_DECREASE ->
            R.string.feature_home_market_reason_gold_18k_decrease

        MarketReason.GOLD_18K_MINOR_CHANGE ->
            R.string.feature_home_market_reason_gold_18k_minor_change

        MarketReason.GOLD_OUNCE_SIGNIFICANT_INCREASE ->
            R.string.feature_home_market_reason_gold_ounce_significant_increase

        MarketReason.GOLD_OUNCE_INCREASE ->
            R.string.feature_home_market_reason_gold_ounce_increase

        MarketReason.GOLD_OUNCE_SIGNIFICANT_DECREASE ->
            R.string.feature_home_market_reason_gold_ounce_significant_decrease

        MarketReason.GOLD_OUNCE_DECREASE ->
            R.string.feature_home_market_reason_gold_ounce_decrease

        MarketReason.GOLD_OUNCE_MINOR_CHANGE ->
            R.string.feature_home_market_reason_gold_ounce_minor_change

        MarketReason.CRYPTO_STRONG_POSITIVE ->
            R.string.feature_home_market_reason_crypto_strong_positive

        MarketReason.CRYPTO_MODERATE_POSITIVE ->
            R.string.feature_home_market_reason_crypto_moderate_positive

        MarketReason.CRYPTO_WEAK_POSITIVE ->
            R.string.feature_home_market_reason_crypto_weak_positive

        MarketReason.CRYPTO_STRONG_NEGATIVE ->
            R.string.feature_home_market_reason_crypto_strong_negative

        MarketReason.CRYPTO_MODERATE_NEGATIVE ->
            R.string.feature_home_market_reason_crypto_moderate_negative

        MarketReason.CRYPTO_WEAK_NEGATIVE ->
            R.string.feature_home_market_reason_crypto_weak_negative

        MarketReason.CRYPTO_MIXED ->
            R.string.feature_home_market_reason_crypto_mixed

        MarketReason.LOCAL_STRONG_POSITIVE ->
            R.string.feature_home_market_reason_local_strong_positive

        MarketReason.LOCAL_STRONG_NEGATIVE ->
            R.string.feature_home_market_reason_local_strong_negative

        MarketReason.LOCAL_MODERATE_POSITIVE ->
            R.string.feature_home_market_reason_local_moderate_positive

        MarketReason.LOCAL_MODERATE_NEGATIVE ->
            R.string.feature_home_market_reason_local_moderate_negative

        MarketReason.LOCAL_WEAK ->
            R.string.feature_home_market_reason_local_weak

        MarketReason.BTC_TREND_RISING ->
            R.string.feature_home_market_reason_btc_trend_rising

        MarketReason.BTC_TREND_FALLING ->
            R.string.feature_home_market_reason_btc_trend_falling

        MarketReason.BTC_TREND_STABLE ->
            R.string.feature_home_market_reason_btc_trend_stable
    }