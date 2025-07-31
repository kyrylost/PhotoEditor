package dev.stukalo.presentation.core.navigation.core.ext

import android.util.Log
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigator

/**
 * Safely navigates to a given route, catching any exceptions that occur during navigation.
 *
 * @param T The type of the route.
 * @param route The route to navigate to.
 * @param navOptions Optional navigation options.
 * @param navigatorExtras Optional navigator extras.
 * @param cleanBackStack Optional parameter to clean the backstack.
 */
fun <T : Any> NavHostController.safeNavigation(
    route: T,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
    cleanBackStack: Boolean = false,
) {
    try {
        if (cleanBackStack) {
            this.popBackStack()
        }
        this.navigate(route, navOptions, navigatorExtras)
    } catch (ex: Exception) {
        Log.e("NavHostController", "Navigation error: ${ex.message}", ex)
    }
}

/**
 * Safely navigates to a given route using a [NavOptionsBuilder], catching any exceptions that occur during navigation.
 *
 * @param T The type of the route.
 * @param route The route to navigate to.
 * @param builder A lambda function to build [NavOptions].
 */
fun <T : Any> NavHostController.safeNavigation(
    route: T,
    builder: NavOptionsBuilder.() -> Unit
) {
    try {
        this.navigate(route, builder)
    } catch (ex: Exception) {
        Log.e("NavHostController", "Navigation error: ${ex.message}", ex)
    }
}

