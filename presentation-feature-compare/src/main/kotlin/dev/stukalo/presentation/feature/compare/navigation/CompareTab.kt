package dev.stukalo.presentation.feature.compare.navigation

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import dev.stukalo.presentation.core.ui.platform.OnActionHandler
import dev.stukalo.presentation.feature.compare.CompareScreen

@Composable
fun CompareTab(
    originalImageBitmap: Bitmap,
    processedImageBitmap: Bitmap?,
    onAction: OnActionHandler,
) {
    CompareScreen(
        originalImageBitmap = originalImageBitmap,
        processedImageBitmap = processedImageBitmap,
        onAction = onAction,
    )
}