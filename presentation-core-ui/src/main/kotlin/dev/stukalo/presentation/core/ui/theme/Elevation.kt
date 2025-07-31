package dev.stukalo.presentation.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

@Immutable
data class PEElevation(
    val small: Dp,
    val normal: Dp,
    val large: Dp,
    val extraLarge: Dp,
)

val LocalPEElevation = staticCompositionLocalOf {
    PEElevation(
        small = Dp.Unspecified,
        normal = Dp.Unspecified,
        large = Dp.Unspecified,
        extraLarge = Dp.Unspecified,
    )
}