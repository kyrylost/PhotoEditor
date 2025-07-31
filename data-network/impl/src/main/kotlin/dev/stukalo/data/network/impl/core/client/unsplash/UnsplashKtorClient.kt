package dev.stukalo.data.network.impl.core.client.unsplash

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest

fun unsplashClient(client: HttpClient) = client.config {
    install(DefaultRequest) {
        url {
            host = "api.unsplash.com"
            parameters.append(
                "client_id",
                "Os_gXnuCBJy2dyqVcISP-3CFFh1waqPPZR1naP4uSQA"
            )
        }
    }
}