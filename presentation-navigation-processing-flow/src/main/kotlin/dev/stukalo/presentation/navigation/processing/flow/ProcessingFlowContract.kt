package dev.stukalo.presentation.navigation.processing.flow

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import dev.stukalo.presentation.core.ui.model.Algorithm
import dev.stukalo.presentation.core.ui.model.AsyncMethod
import dev.stukalo.presentation.core.ui.platform.mvi.SingleEvent
import dev.stukalo.presentation.core.ui.platform.mvi.UiState
import dev.stukalo.presentation.core.ui.platform.mvi.ViewIntent

@Immutable
internal data class ProcessingFlowUiState(
    override val isLoading: Boolean,
    override val isError: Boolean,
    val originalImageBitmap: Bitmap? = null,
    val processedImageBitmap: Bitmap? = null,
): UiState {

    companion object {
        fun initial() = ProcessingFlowUiState(
            isLoading = true,
            isError = false,
        )
    }

}

@Immutable
internal sealed interface ProcessingFlowViewIntent :
    ViewIntent {
    data class GetBitmapFromUrl(
        val originalImageUrl: String
    ) : ProcessingFlowViewIntent

    data class OnNavigateToCompare(
        val processedImageBitmap: Bitmap
    ): ProcessingFlowViewIntent

    data class OnNavigateToLogs(
        val processedImageBitmap: Bitmap,
        val processedImageBytes: Int,
        val usedAlgorithm: Algorithm,
        val usedAsyncMethod: AsyncMethod,
        val compressionTime: Int,
        val compressionLevel: Int,
    ): ProcessingFlowViewIntent
}

@Immutable
internal sealed interface ProcessingFlowSingleEvent : SingleEvent {
    data object NavigateToCompare : ProcessingFlowSingleEvent
    data class NavigateToLogs(
        val processedImageBytes: Int,
        val usedAlgorithm: Algorithm,
        val usedAsyncMethod: AsyncMethod,
        val compressionTime: Int,
        val compressionLevel: Int,
    ) : ProcessingFlowSingleEvent
}