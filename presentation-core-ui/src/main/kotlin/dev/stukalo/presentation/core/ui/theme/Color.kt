package dev.stukalo.presentation.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Blue = Color(0xFF00B2FF)
val DarkBlue = Color(0xFF1D3D57)

val Gray = Color(0xFFB3BCC4)
//val LightGray = Color(0xFFC3CED7)

val Red = Color(0xFFFE5353)

val Neutral0 = Color(0xFF1E1E18)
val Neutral10 = Color(0xFF26261E)
val Neutral90 = Color(0xFF3D3D36)

@Immutable
data class PEColors(
    val primary: Color,
    val background: Color,
    val surface: Color,
    val icon: Color,
    val textTitle: Color,
    val textBody: Color,
    val error: Color,
    val shadow: Color,
)

val LocalPEColors = staticCompositionLocalOf {
    PEColors(
        primary = Color.Unspecified,
        background = Color.Unspecified,
        surface = Color.Unspecified,
        icon = Color.Unspecified,
        textTitle = Color.Unspecified,
        textBody = Color.Unspecified,
        error = Color.Unspecified,
        shadow = Color.Unspecified,
    )
}

internal val PEColorsLight = PEColors(
    primary = Blue,
    background = Color.White,
    surface = Color.White,
    icon = Gray,
    textTitle = DarkBlue,
    textBody = Gray,
    error = Red,
    shadow = Gray,
)

internal val PEColorsDark = PEColors(
    primary = Blue,
    background = Neutral10,
    surface = Neutral90,
    icon = Gray,
    textTitle = Color.White,
    textBody = Color.White,
    error = Red,
    shadow = Neutral0,
)

