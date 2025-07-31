package dev.stukalo.presentation.navigation.main.flow.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.stukalo.presentation.core.navigation.source.NavigationDirection
import dev.stukalo.presentation.navigation.main.flow.MainFlowScreen

fun NavGraphBuilder.mainFlowRoute(
    navController: NavHostController,
) {
    composable<NavigationDirection.MainFlow> {
        MainFlowScreen(
            appNavController = navController,
        )
    }
}
