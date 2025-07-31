package dev.stukalo.presentation.feature.compare.navigation

import dev.stukalo.presentation.core.ui.platform.ScreenAction

sealed interface CompareScreenAction : ScreenAction {

    data object GoBack : CompareScreenAction
}