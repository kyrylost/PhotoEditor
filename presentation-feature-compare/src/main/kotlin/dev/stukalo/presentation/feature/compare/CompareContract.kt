package dev.stukalo.presentation.feature.compare

import androidx.compose.runtime.Immutable
import dev.stukalo.presentation.core.ui.platform.mvi.SingleEvent
import dev.stukalo.presentation.core.ui.platform.mvi.UiState
import dev.stukalo.presentation.core.ui.platform.mvi.ViewIntent

@Immutable
internal data class CompareScreenScreenState(
    override val isLoading: Boolean,
    override val isError: Boolean,
) : UiState {

    companion object {
        fun initial() = CompareScreenScreenState(
            isLoading = false,
            isError = false,
        )
    }

}

@Immutable
internal sealed interface CompareScreenViewIntent :
    ViewIntent {
}

@Immutable
internal sealed interface CompareScreenSingleEvent : SingleEvent {
}