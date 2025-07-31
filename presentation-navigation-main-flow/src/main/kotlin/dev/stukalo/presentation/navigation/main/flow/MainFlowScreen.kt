package dev.stukalo.presentation.navigation.main.flow

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.stukalo.presentation.core.ui.components.PESpacer
import dev.stukalo.presentation.core.ui.theme.LocalPEColors
import dev.stukalo.presentation.core.navigation.core.ext.safeNavigation
import dev.stukalo.presentation.navigation.main.flow.bottomnav.NavigationItem
import dev.stukalo.presentation.navigation.main.flow.navigation.inner.InnerMainNavigationGraph

@Composable
internal fun MainFlowScreen(
    modifier: Modifier = Modifier,
    appNavController: NavHostController,
) {
    val mainNavController = rememberNavController()

    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val navItems = listOf(
        NavigationItem.Search(),
        NavigationItem.Downloads(),
    )

    Box(modifier) {
        InnerMainNavigationGraph(
            mainNavController = mainNavController,
            appNavController = appNavController,
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            PESpacer(1f)

            Box(
                modifier = Modifier
                    .clickable(enabled = false){}
                    .navigationBarsPadding()
                    .clip(
                        RoundedCornerShape(
                            topStart = 24.dp, topEnd = 24.dp,
                            bottomStart = 0.dp, bottomEnd = 0.dp
                        )
                    )
                    .background(LocalPEColors.current.background),
            ) {
                Row(
                    modifier = Modifier
                        .padding(vertical = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    navItems.forEach { item ->
                        NavigationItem(
                            navigationItem = item,
                            isSelected = currentDestination?.hierarchy?.any { destinationHierarchy ->
                                destinationHierarchy.hasRoute(item.route::class)
                            } == true,
                            onClick = {
                                mainNavController.safeNavigation(route = item.route) {
                                    mainNavController.graph.startDestinationRoute?.let { route ->
                                        popUpTo(route) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
