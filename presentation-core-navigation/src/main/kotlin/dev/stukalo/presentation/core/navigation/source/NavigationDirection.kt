package dev.stukalo.presentation.core.navigation.source

import kotlinx.serialization.Serializable

/**
 * Represents different navigation directions within the application.
 *
 * This sealed class defines various destinations within the app, each associated with a specific
 * feature module.
 */
@Serializable
sealed interface NavigationDirection {

    @Serializable
    data object MainFlow: NavigationDirection

    @Serializable
    data class ProcessingFlow(
        val url: String
    ): NavigationDirection

}
