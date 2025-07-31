package dev.stukalo.presentation.core.ui.util

import androidx.compose.foundation.pager.PageSize
import androidx.compose.ui.unit.Density

val threePagesPerViewport = object : PageSize {
    override fun Density.calculateMainAxisPageSize(
        availableSpace: Int,
        pageSpacing: Int
    ): Int {
        return (availableSpace - 2 * pageSpacing) / 3
    }
}
