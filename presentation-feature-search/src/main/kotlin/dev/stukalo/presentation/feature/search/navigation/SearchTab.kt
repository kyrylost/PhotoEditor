package dev.stukalo.presentation.feature.search.navigation

import androidx.compose.runtime.Composable
import dev.stukalo.presentation.core.ui.platform.OnActionHandler
import dev.stukalo.presentation.feature.search.SearchScreen

@Composable
fun SearchTab(
    onAction: OnActionHandler,
) = SearchScreen(
        onAction = {
            when (it) {
                is SearchScreenAction.GoToProcessing ->
                    onAction(SearchScreenAction.GoToProcessing(it.imageUrl))
            }
        }
    )
