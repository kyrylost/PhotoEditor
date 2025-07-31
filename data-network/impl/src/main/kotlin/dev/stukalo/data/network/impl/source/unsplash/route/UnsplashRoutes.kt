package dev.stukalo.data.network.impl.source.unsplash.route


sealed class UnsplashRoutes(val route: String) {

    data object Search: UnsplashRoutes("/search/photos")

}