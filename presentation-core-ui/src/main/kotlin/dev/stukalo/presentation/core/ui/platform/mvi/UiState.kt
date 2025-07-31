package dev.stukalo.presentation.core.ui.platform.mvi

import androidx.compose.runtime.Immutable

@Immutable
interface UiState {
    val isLoading: Boolean
    val isError: Boolean
}