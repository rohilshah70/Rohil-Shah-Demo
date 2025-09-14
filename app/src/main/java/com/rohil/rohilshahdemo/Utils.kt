package com.rohil.rohilshahdemo

import java.util.Locale

fun Double.roundTo2Decimal(): Double {
    return String.format(Locale.ROOT,"%.2f", this).toDouble()
}