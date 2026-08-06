package com.aliayali.home.formatter


import java.text.NumberFormat
import java.util.Locale

fun Long.formatPrice(): String {
    return NumberFormat
        .getNumberInstance(Locale.US)
        .format(this)
}

fun Double.formatPrice(): String {
    return NumberFormat
        .getNumberInstance(Locale.US)
        .format(this)
}