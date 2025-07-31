package dev.stukalo.data.network.impl.core.client.base

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val TIME_OUT = 15_000L

fun client() = HttpClient(Android) {

    expectSuccess = true

    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }

    install(HttpTimeout) {
        connectTimeoutMillis = TIME_OUT
        requestTimeoutMillis = TIME_OUT
        socketTimeoutMillis = TIME_OUT
    }

    install(Logging) {
        logger = Logger.ANDROID
        level = LogLevel.BODY
    }

    install(DefaultRequest) {
        headers.append("Content-Type", "application/json")
        headers.append("platform", "android")
        url {
            protocol = URLProtocol.HTTPS
        }
    }

}
