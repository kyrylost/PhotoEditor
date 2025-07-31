package dev.stukalo.presentation.navigation.processing.flow.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.stukalo.presentation.core.navigation.source.NavigationDirection
import dev.stukalo.presentation.navigation.processing.flow.ProcessingFlowScreen

fun NavGraphBuilder.processingFlowRoute(
    navController: NavHostController,
) {
    composable<NavigationDirection.ProcessingFlow> {
        val args = it.toRoute<NavigationDirection.ProcessingFlow>()

        ProcessingFlowScreen(
            modifier = Modifier,
            imageUrl = args.url,
            appNavController = navController
        )
    }
}