package dev.stukalo.presentation.feature.compressor.navigation

import android.graphics.Bitmap
import dev.stukalo.presentation.core.ui.model.Algorithm
import dev.stukalo.presentation.core.ui.model.AsyncMethod
import dev.stukalo.presentation.core.ui.platform.ScreenAction

sealed interface CompressorScreenAction : ScreenAction {

    data object GoBack : CompressorScreenAction

    data class GoToCompare(
        val processedImage: Bitmap,
    ) : CompressorScreenAction

    data class GoToLogs(
        val processedImage: Bitmap,
        val processedImageBytes: Int,
        val usedAlgorithm: Algorithm,
        val usedAsyncMethod: AsyncMethod,
        val compressionTime: Int,
        val compressionLevel: Int = 0,
    ) : CompressorScreenAction
}