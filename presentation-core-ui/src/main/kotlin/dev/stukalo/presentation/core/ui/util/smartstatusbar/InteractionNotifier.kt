package dev.stukalo.presentation.core.ui.util.smartstatusbar

import kotlinx.coroutines.flow.MutableSharedFlow

internal val interactionFlow: MutableSharedFlow<Unit> = MutableSharedFlow()

suspend fun notifyAboutInteraction() {
    interactionFlow.emit(Unit)
}