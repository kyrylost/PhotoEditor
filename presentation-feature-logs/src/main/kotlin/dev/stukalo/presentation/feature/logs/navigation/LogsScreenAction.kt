package dev.stukalo.presentation.feature.logs.navigation

import dev.stukalo.presentation.core.ui.platform.ScreenAction

sealed interface LogsScreenAction : ScreenAction {

    data object GoBack : LogsScreenAction
}