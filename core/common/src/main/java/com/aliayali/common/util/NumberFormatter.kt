package com.aliayali.common.util

import java.text.NumberFormat
import java.util.Locale

fun Double.formattedPrice(): String =
    NumberFormat
        .getNumberInstance(Locale.US)
        .apply {
            maximumFractionDigits = 0
            minimumFractionDigits = 0
        }
        .format(this)

fun Double.formattedDollarPrice(): String =
    "$${formattedPrice()}"

fun Double.formattedPercent(): String =
    "${if (this >= 0) "+" else ""}${"%.2f".format(Locale.US, this)}%"