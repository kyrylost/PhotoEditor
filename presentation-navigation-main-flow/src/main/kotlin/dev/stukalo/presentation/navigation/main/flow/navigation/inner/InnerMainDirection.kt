package dev.stukalo.presentation.navigation.main.flow.navigation.inner

import kotlinx.serialization.Serializable

@Serializable
sealed class InnerMainDirection {

    @Serializable
    data object Search : InnerMainDirection()

    @Serializable
    data object Downloads : InnerMainDirection()

}