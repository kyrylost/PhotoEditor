package dev.stukalo.presentation.feature.search.navigation

import dev.stukalo.presentation.core.ui.platform.ScreenAction

sealed interface SearchScreenAction : ScreenAction {

    data class GoToProcessing(
        val imageUrl: String,
    ) : SearchScreenAction

}