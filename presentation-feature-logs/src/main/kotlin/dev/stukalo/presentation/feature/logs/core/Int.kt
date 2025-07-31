package dev.stukalo.presentation.feature.logs.core

import kotlin.math.round

fun Int.bytesToMegabytes(decimals: Int = 2): Double {
    val megabytes = this / 1024f / 1024f
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(megabytes * multiplier) / multiplier
}