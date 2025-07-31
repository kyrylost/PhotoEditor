package dev.stukalo.presentation.feature.logs.navigation

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import dev.stukalo.presentation.core.ui.model.Algorithm
import dev.stukalo.presentation.core.ui.model.AsyncMethod
import dev.stukalo.presentation.core.ui.platform.OnActionHandler
import dev.stukalo.presentation.feature.logs.LogsScreen

@Composable
fun LogsTab(
    originalImageBitmap: Bitmap,
    processedImageBitmap: Bitmap?,
    processedImageBytes: Int,
    usedAlgorithm: Algorithm,
    usedAsyncMethod: AsyncMethod,
    compressionTime: Int,
    compressionLevel: Int,
    onAction: OnActionHandler,
) {
    LogsScreen(
        processedImageBytes = processedImageBytes,
        usedAlgorithm = usedAlgorithm,
        usedAsyncMethod = usedAsyncMethod,
        compressionTime = compressionTime,
        compressionLevel = compressionLevel,
        originalImageBitmap = originalImageBitmap,
        processedImageBitmap = processedImageBitmap,
        onAction = onAction,
    )
}