package dev.stukalo.presentation.feature.downloads

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import dev.stukalo.presentation.core.ui.platform.mvi.SingleEvent
import dev.stukalo.presentation.core.ui.platform.mvi.UiState
import dev.stukalo.presentation.core.ui.platform.mvi.ViewIntent

@Immutable
internal data class DownloadsScreenScreenState(
    override val isLoading: Boolean,
    override val isError: Boolean,
    val downloads: List<Bitmap>,
) : UiState {

    companion object {
        fun initial() = DownloadsScreenScreenState(
            isLoading = true,
            isError = false,
            downloads = emptyList()
        )
    }

}

@Immutable
internal sealed interface DownloadsScreenViewIntent :
    ViewIntent {
    data object OnLoadDownloads: DownloadsScreenViewIntent
}

@Immutable
internal sealed interface DownloadsScreenSingleEvent : SingleEvent {
}