package dev.stukalo.data.network.impl.source.unsplash

import dev.stukalo.data.network.impl.source.base.BaseNetSource
import dev.stukalo.data.network.impl.source.unsplash.route.UnsplashRoutes
import dev.stukalo.data.network.unsplash.model.SearchResponseDataModel
import dev.stukalo.data.network.unsplash.source.UnsplashNetSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal class UnsplashNetSourceImpl(
    client: HttpClient,
): BaseNetSource(client), UnsplashNetSource {

    override suspend fun get(
        query: String,
        sortOrder: String,
        page: Int,
        perPage: Int,
    ): SearchResponseDataModel {
        return performRequest {
            get(UnsplashRoutes.Search.route) {
                parameter("query", query)
                parameter("order_by", sortOrder)
                parameter("page", page)
                parameter("per_page", perPage)
            }
        }
    }
}