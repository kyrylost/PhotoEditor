package dev.stukalo.presentation.feature.compressor

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import androidx.compose.ui.layout.ContentScale
import dev.stukalo.presentation.core.ui.model.Algorithm
import dev.stukalo.presentation.core.ui.model.AsyncMethod
import dev.stukalo.presentation.core.ui.platform.mvi.SingleEvent
import dev.stukalo.presentation.core.ui.platform.mvi.UiState
import dev.stukalo.presentation.core.ui.platform.mvi.ViewIntent

@Immutable
internal data class CompressorScreenUiState(
    override val isLoading: Boolean,
    override val isError: Boolean,
    val isImageProcessing: Boolean,
    val processedImage: Bitmap?,
    val contentScale: ContentScale,
    val compressionLevel: Int,
    val isCompressionViewVisible: Boolean,
    val isCompressionSliderVisible: Boolean,
    val isAlgorithmPagerVisible: Boolean,
    val isAsyncViewVisible: Boolean,
    val isAsyncPagerVisible: Boolean,
    val algorithmPagerList: List<Algorithm>,
    val asyncMethodPagerList: List<AsyncMethod>,
) : UiState {

    companion object {
        fun initial() = CompressorScreenUiState(
            isLoading = true,
            isError = false,
            isImageProcessing = false,
            processedImage = null,
            contentScale = ContentScale.Crop,
            compressionLevel = 50,
            isCompressionViewVisible = false,
            isCompressionSliderVisible = true,
            isAlgorithmPagerVisible = true,
            isAsyncViewVisible = false,
            isAsyncPagerVisible = true,
            algorithmPagerList = algorithmPagerList,
            asyncMethodPagerList = asyncMethodPagerList,
        )
    }

}

@Immutable
internal sealed interface CompressorScreenViewIntent :
    ViewIntent {
    data class OnLaunch(val originalImageBitmap: Bitmap) : CompressorScreenViewIntent

    data object OnNavigateBackClick : CompressorScreenViewIntent
    data object OnNavigateToCompareClick : CompressorScreenViewIntent
    data object NavigateToLogsClick : CompressorScreenViewIntent
    data object OnDownloadClick : CompressorScreenViewIntent
    data object OnChangeContentScale : CompressorScreenViewIntent

    data object OnChangeCompressionVisibility : CompressorScreenViewIntent
    data object OnChangeAlgorithmVisibility : CompressorScreenViewIntent
    data object OnChangeAsyncVisibility : CompressorScreenViewIntent

    data class OnAlgorithmPagerScroll(
        val page: Int
    ): CompressorScreenViewIntent

    data class OnAsyncMethodPagerScroll(
        val page: Int
    ): CompressorScreenViewIntent

    data class OnChangeCompressionLevel(
        val level: Float
    ): CompressorScreenViewIntent
}

@Immutable
internal sealed interface CompressorScreenSingleEvent : SingleEvent {
    data class Error(
        val message: String
    ) : CompressorScreenSingleEvent

    data object NavigateBack : CompressorScreenSingleEvent

    data class NavigateToCompareClick(
        val processedImage: Bitmap
    ) : CompressorScreenSingleEvent

    data class NavigateToLogsClick(
        val processedImage: Bitmap,
        val processedImageBytes: Int,
        val usedAlgorithm: Algorithm,
        val usedAsyncMethod: AsyncMethod,
        val compressionTime: Int,
        val compressionLevel: Int = 0,
    ) : CompressorScreenSingleEvent
}

private val algorithmPagerList = listOf(
    Algorithm.Downsampling,
    Algorithm.DownsamplingRLE,
    Algorithm.None,
    Algorithm.RunLengthEncoding,
    Algorithm.Downsampling,
    Algorithm.DownsamplingRLE,
    Algorithm.None,
    Algorithm.RunLengthEncoding,
)

private val asyncMethodPagerList = listOf(
    AsyncMethod.Java,
    AsyncMethod.NonAsyncRun,
    AsyncMethod.Coroutines,
    AsyncMethod.AsyncTask,
    AsyncMethod.Java,
    AsyncMethod.NonAsyncRun,
    AsyncMethod.Coroutines,
    AsyncMethod.AsyncTask,
)