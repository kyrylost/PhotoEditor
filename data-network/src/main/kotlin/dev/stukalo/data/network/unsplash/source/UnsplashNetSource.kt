package dev.stukalo.data.network.unsplash.source

import dev.stukalo.data.network.unsplash.model.SearchResponseDataModel

interface UnsplashNetSource {
    suspend fun get(
        query: String,
        sortOrder: String,
        page: Int,
        perPage: Int,
    ): SearchResponseDataModel
}