package dev.stukalo.presentation.feature.compare

import dev.stukalo.presentation.core.ui.platform.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

internal class CompareViewModel(

) : BaseViewModel<CompareScreenScreenState, CompareScreenViewIntent, CompareScreenSingleEvent>() {
    override val _state: MutableStateFlow<CompareScreenScreenState> =
        MutableStateFlow(CompareScreenScreenState.initial())
    override val state: StateFlow<CompareScreenScreenState> = _state.asStateFlow()
    override val _singleEvent: MutableSharedFlow<CompareScreenSingleEvent> = MutableSharedFlow()
    override val singleEvent: SharedFlow<CompareScreenSingleEvent> = _singleEvent.asSharedFlow()

    override fun consumeIntent(intent: CompareScreenViewIntent) = Unit
}