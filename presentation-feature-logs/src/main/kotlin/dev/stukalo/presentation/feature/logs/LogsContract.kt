package dev.stukalo.presentation.feature.logs

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import dev.stukalo.presentation.core.ui.model.Algorithm
import dev.stukalo.presentation.core.ui.platform.mvi.SingleEvent
import dev.stukalo.presentation.core.ui.platform.mvi.UiState
import dev.stukalo.presentation.core.ui.platform.mvi.ViewIntent
import dev.stukalo.presentation.feature.logs.model.ChartType

@Immutable
internal data class LogsScreenScreenState(
    override val isLoading: Boolean,
    override val isError: Boolean,
    val selectedChartType: ChartType = ChartType.TIME,

    val asyncTaskCompressionTime: Long = 0L,
    val coroutinesCompressionTime: Long = 0L,
    val javaCompressionTime: Long = 0L,
    val nonAsyncCompressionTime: Long = 0L,

    val rleCR: Float = 0F,
    val downsamplingCR: Float = 0F,
    val downsamplingRleCR: Float = 0F,

    val rleMSE: Float = 0F,
    val downsamplingMSE: Float = 0F,
    val downsamplingRleMSE: Float = 0F,
) : UiState {

    companion object {
        fun initial() = LogsScreenScreenState(
            isLoading = true,
            isError = false,
        )
    }

}

@Immutable
internal sealed interface LogsScreenViewIntent :
    ViewIntent {
    data class OnSelectChartType(
        val chartType: ChartType,
    ): LogsScreenViewIntent

    data class TimeChartTypeSelected(
        val originalBitmap: Bitmap,
        val usedAlgorithm: Algorithm,
        val compressionLevel: Int,
    ): LogsScreenViewIntent

    data class MSEChartTypeSelected(
        val originalBitmap: Bitmap,
        val compressedBitmap: Bitmap,
        val usedAlgorithm: Algorithm,
        val compressionLevel: Int,
    ): LogsScreenViewIntent

    data class CRChartTypeSelected(
        val originalBitmap: Bitmap,
        val compressedBitmapSize: Int,
        val usedAlgorithm: Algorithm,
        val compressionLevel: Int,
    ): LogsScreenViewIntent

    data object OnNavigateBack: LogsScreenViewIntent
}

@Immutable
internal sealed interface LogsScreenSingleEvent : SingleEvent {
    data object NavigateBack: LogsScreenSingleEvent

    data class OnChartTypeSelected(
        val chartType: ChartType,
    ): LogsScreenSingleEvent
}