package dev.stukalo.presentation.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import dev.stukalo.presentation.core.ui.R

val Fredoka = FontFamily(
    Font(R.font.fredoka_light, weight = FontWeight.Light),
    Font(R.font.fredoka_regular, weight = FontWeight.Normal),
    Font(R.font.fredoka_medium, weight = FontWeight.Medium),
    Font(R.font.fredoka_semibold, weight = FontWeight.SemiBold),
    Font(R.font.fredoka_bold, weight = FontWeight.Bold),
)

@Immutable
data class PETypography(
    val titleLarge: TextStyle,
    val titleNormal: TextStyle,
    val titleSmall: TextStyle,
    val bodyLarge: TextStyle,
    val bodyNormal: TextStyle,
)

val LocalPETypography = staticCompositionLocalOf {
    PETypography(
        titleLarge = TextStyle.Default,
        titleNormal = TextStyle.Default,
        titleSmall = TextStyle.Default,
        bodyLarge = TextStyle.Default,
        bodyNormal = TextStyle.Default,
    )
}

