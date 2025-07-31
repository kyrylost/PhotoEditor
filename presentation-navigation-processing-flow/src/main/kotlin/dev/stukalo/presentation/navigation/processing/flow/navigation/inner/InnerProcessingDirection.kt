package dev.stukalo.presentation.navigation.processing.flow.navigation.inner

import dev.stukalo.presentation.core.ui.model.Algorithm
import dev.stukalo.presentation.core.ui.model.AsyncMethod
import kotlinx.serialization.Serializable

@Serializable
sealed class InnerProcessingDirection {

    @Serializable
    data object Compressor : InnerProcessingDirection()

    @Serializable
    data object Compare : InnerProcessingDirection()

    @Serializable
    data class Logs(
        val processedImageBytes: Int,
        val usedAlgorithm: Algorithm,
        val usedAsyncMethod: AsyncMethod,
        val compressionTime: Int,
        val compressionLevel: Int,
    )

}