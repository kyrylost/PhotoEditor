package dev.stukalo.presentation.feature.host

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.stukalo.presentation.core.navigation.source.NavigationDirection
import dev.stukalo.presentation.navigation.main.flow.navigation.mainFlowRoute
import dev.stukalo.presentation.navigation.processing.flow.navigation.processingFlowRoute


@Composable
fun PENavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDirection.MainFlow,
        modifier = modifier
    ) {
        mainFlowRoute(
            navController = navController
        )

        processingFlowRoute(
            navController = navController
        )
    }
}