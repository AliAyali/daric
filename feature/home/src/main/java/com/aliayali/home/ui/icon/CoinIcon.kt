package com.aliayali.home.ui.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CurrencyBitcoin
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Euro
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.ui.graphics.vector.ImageVector

fun coinIcon(symbol: String): ImageVector {
    return when (symbol.uppercase()) {
        "BTC" -> Icons.Default.CurrencyBitcoin
        "ETH" -> Icons.Default.Diamond
        "USD" -> Icons.Default.AttachMoney
        "EUR" -> Icons.Default.Euro
        "XAU", "GOLD" -> Icons.Default.MonetizationOn
        else -> Icons.Default.AttachMoney
    }
}