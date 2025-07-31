package dev.stukalo.presentation.feature.compressor.navigation

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import dev.stukalo.presentation.core.ui.platform.OnActionHandler
import dev.stukalo.presentation.feature.compressor.CompressorScreen

@Composable
fun CompressorTab(
    originalImageBitmap: Bitmap,
    onAction: OnActionHandler = {},
) {
    CompressorScreen(
        originalImageBitmap = originalImageBitmap,
        onAction = onAction,
    )
}