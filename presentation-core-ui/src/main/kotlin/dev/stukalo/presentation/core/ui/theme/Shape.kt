package dev.stukalo.presentation.core.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

@Immutable
data class PEShape(
    val largeRoundedCornerShape: RoundedCornerShape,
    val normalRoundedCornerShape: RoundedCornerShape
)

val LocalPEShape = staticCompositionLocalOf {
    PEShape(
        largeRoundedCornerShape = RoundedCornerShape(Dp.Unspecified),
        normalRoundedCornerShape = RoundedCornerShape(Dp.Unspecified)
    )
}