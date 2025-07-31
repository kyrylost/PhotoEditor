package dev.stukalo.presentation.navigation.main.flow.bottomnav

import androidx.annotation.DrawableRes
import dev.stukalo.presentation.navigation.main.flow.R
import dev.stukalo.presentation.navigation.main.flow.navigation.inner.InnerMainDirection
import dev.stukalo.presentation.core.ui.R.drawable as RDrawable

sealed class NavigationItem(
    @DrawableRes open val icon: Int,
    open val route: Any,
) {
    data class Search(
        override val icon: Int = R.drawable.ic_search,
        override val route: InnerMainDirection.Search = InnerMainDirection.Search,
    ): NavigationItem(
        icon,
        route,
    )

    data class Downloads(
        override val icon: Int = RDrawable.ic_download,
        override val route: InnerMainDirection.Downloads = InnerMainDirection.Downloads,
    ): NavigationItem(
        icon,
        route,
    )
}