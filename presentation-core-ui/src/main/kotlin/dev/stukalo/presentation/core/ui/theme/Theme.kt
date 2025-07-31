package dev.stukalo.presentation.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun PETheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colors: PEColors = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) PEColorsDark else PEColorsLight
        }

        darkTheme -> PEColorsDark
        else -> PEColorsLight
    }

    val typography = PETypography(
        titleLarge = TextStyle(
            fontFamily = Fredoka,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
            color = colors.textTitle,
        ),
        titleNormal = TextStyle(
            fontFamily = Fredoka,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 21.sp,
            letterSpacing = 0.5.sp,
            color = colors.textTitle,
        ),
        titleSmall = TextStyle(
            fontFamily = Fredoka,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.5.sp,
            color = colors.textTitle,
        ),
        bodyLarge = TextStyle(
            fontFamily = Fredoka,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 21.sp,
            letterSpacing = 0.5.sp,
            color = colors.textBody,
        ),
        bodyNormal = TextStyle(
            fontFamily = Fredoka,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.5.sp,
            color = colors.textBody,
        ),
    )

    val elevation = PEElevation(
        small = 4.dp,
        normal = 8.dp,
        large = 12.dp,
        extraLarge = 16.dp,
    )

    val shape = PEShape(
        largeRoundedCornerShape = RoundedCornerShape(24.dp),
        normalRoundedCornerShape = RoundedCornerShape(16.dp),
    )

    CompositionLocalProvider(
        LocalPEColors provides colors,
        LocalPETypography provides typography,
        LocalPEElevation provides elevation,
        LocalPEShape provides shape,
        content = content
    )
}

object PETheme {
    val colors: PEColors
        @Composable
        get() = LocalPEColors.current
    val typography: PETypography
        @Composable
        get() = LocalPETypography.current
    val elevation: PEElevation
        @Composable
        get() = LocalPEElevation.current
    val shape: PEShape
        @Composable
        get() = LocalPEShape.current
}