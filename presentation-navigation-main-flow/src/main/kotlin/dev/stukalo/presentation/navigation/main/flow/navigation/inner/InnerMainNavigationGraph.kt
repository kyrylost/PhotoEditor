package dev.stukalo.presentation.navigation.main.flow.navigation.inner

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.stukalo.presentation.core.navigation.core.ext.safeNavigation
import dev.stukalo.presentation.core.navigation.source.NavigationDirection
import dev.stukalo.presentation.feature.downloads.navigation.DownloadsTab
import dev.stukalo.presentation.feature.search.navigation.SearchScreenAction
import dev.stukalo.presentation.feature.search.navigation.SearchTab

@Composable
fun InnerMainNavigationGraph(
    modifier: Modifier = Modifier,
    mainNavController: NavHostController,
    appNavController: NavHostController,
) {
    NavHost(
        navController = mainNavController,
        startDestination = InnerMainDirection.Search,
        modifier = modifier
    ) {
        composable<InnerMainDirection.Search> {
            SearchTab{
                when (it) {
                    is SearchScreenAction.GoToProcessing ->
                        appNavController.safeNavigation(
                            NavigationDirection.ProcessingFlow(it.imageUrl)
                        )
                }
            }
        }

        composable<InnerMainDirection.Downloads> {
            DownloadsTab()
        }

    }
}