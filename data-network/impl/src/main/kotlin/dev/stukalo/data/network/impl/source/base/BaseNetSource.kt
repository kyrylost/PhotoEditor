package dev.stukalo.data.network.impl.source.base

import dev.stukalo.common.core.exception.ApiException
import dev.stukalo.common.core.exception.isConnectionError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.isSuccess

/**
 * A base class for performing network requests using [HttpClient].
 *
 * This abstract class provides a method to perform a network request and handle response status codes.
 * It also includes error handling for common HTTP errors such as Unauthorized or Forbidden.
 *
 * @param httpClient An instance of [HttpClient] used to perform network requests.
 */
internal abstract class BaseNetSource(
    val httpClient: HttpClient,
) {

    protected suspend inline fun <reified T> performRequest(
        errorTransformer: (requestUrl: String, httpCode: Int, cause: Exception?) -> Throwable = ::transformError,
        request: HttpClient.() -> HttpResponse,
    ): T {
        return try {
            val response = httpClient.request()
            if (response.status.isSuccess()) {
                if (T::class == String::class) {
                    response.bodyAsText() as T
                } else {
                    response.body()
                }
            } else {
                val requestUrl = response.request.url.toString()
                val httpCode = response.status.value
                throw errorTransformer(requestUrl, httpCode, null)
            }
        } catch (e: Exception) {
            val response = (e as? ResponseException)?.response
            val requestUrl = response?.request?.url.toString()
            val httpCode = response?.status?.value ?: -1
            throw errorTransformer(requestUrl, httpCode, e)
        }
    }

    protected open fun transformError(
        requestUrl: String,
        httpCode: Int,
        cause: Exception?,
    ): Throwable {
        return when {
            cause.isConnectionError -> ApiException.ConnectionApiException()

            httpCode == 401 || httpCode == 403 -> ApiException.ClientError.NotAuthorizedException(requestUrl)

            httpCode == 404 -> ApiException.ClientError.NotFoundException(requestUrl)

            httpCode == 422 -> ApiException.ClientError.UnprocessableEntityException(requestUrl)

            httpCode in 400..499 -> ApiException.ClientError.UnknownClientApiException(requestUrl, httpCode)

            httpCode == 500 -> ApiException.ServerError.InternalServerError(requestUrl)

            httpCode == 502 -> ApiException.ServerError.BadGatewayException(requestUrl)

            httpCode == 503 -> ApiException.ServerError.ServiceUnavailableException(requestUrl)

            httpCode in 500..599 -> ApiException.ServerError.UnknownServerApiException(requestUrl, httpCode)

            else -> ApiException.UnknownApiException(httpCode)
        }
    }
}