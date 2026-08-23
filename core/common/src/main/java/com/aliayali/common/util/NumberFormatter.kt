package com.aliayali.common.util

import java.text.NumberFormat
import java.util.Locale

fun Double.formattedPrice(): String =
    NumberFormat
        .getNumberInstance(Locale.US)
        .format(this)

fun Double.formattedPercent(): String =
    "${if (this >= 0) "+" else ""}${"%.2f".format(Locale.US, this)}%"