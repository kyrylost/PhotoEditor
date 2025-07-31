package dev.stukalo.common.core.exception

/**
 * Represents an exception related to an API request.
 *
 * This class extends [AppException] and adds additional properties to capture the request URL and HTTP code
 * associated with the error. It is intended for use in situations where an API request fails or encounters issues.
 *
 * @param requestUrl The URL of the API request that caused the exception.
 * @param httpCode The HTTP status code returned by the API.
 * @param message The detail message for this exception, or `null` if none.
 * @param cause The cause of this exception, or `null` if none.
 */
sealed class ApiException(
    open val requestUrl: String? = null,
    open val httpCode: Int? = null,
    override val message: String? = null,
    override val cause: Throwable? = null,
) : AppException(message, cause) {

    /**
     * 4xx – Client Error family
     */
    sealed class ClientError(
        override val requestUrl: String?,
        override val httpCode: Int,
        override val message: String?,
        override val cause: Throwable?
    ) : ApiException(requestUrl, httpCode, message, cause) {

        class NotAuthorizedException(
            requestUrl: String,
            message: String = "Unauthorized",
            cause: Throwable? = null,
        ) : ClientError(requestUrl, 401, message, cause)

        class NotFoundException(
            requestUrl: String,
            message: String = "Not Found",
            cause: Throwable? = null,
        ) : ClientError(requestUrl, 404, message, cause)

        class UnprocessableEntityException(
            requestUrl: String,
            message: String = "Unprocessable Entity",
            cause: Throwable? = null,
        ) : ClientError(requestUrl, 422, message, cause)

        class UnknownClientApiException(
            requestUrl: String?,
            httpCode: Int,
            message: String = "Unknown client error. Code: $httpCode",
            cause: Throwable? = null,
        ) : ClientError(requestUrl, httpCode, message, cause)
    }

    /**
     * 5xx – Server Error family
     */
    sealed class ServerError(
        override val requestUrl: String?,
        override val httpCode: Int,
        override val message: String?,
        override val cause: Throwable?
    ) : ApiException(requestUrl, httpCode, message, cause) {

        class InternalServerError(
            requestUrl: String?,
            message: String = "Internal Server Error",
            cause: Throwable? = null,
        ) : ServerError(requestUrl, 500, message, cause)

        class BadGatewayException(
            requestUrl: String?,
            message: String = "Bad Gateway",
            cause: Throwable? = null,
        ) : ServerError(requestUrl, 502, message, cause)

        class ServiceUnavailableException(
            requestUrl: String?,
            message: String = "Service Unavailable",
            cause: Throwable? = null,
        ) : ServerError(requestUrl, 503, message, cause)

        class UnknownServerApiException(
            requestUrl: String?,
            httpCode: Int,
            message: String = "Unknown server error. Code: $httpCode",
            cause: Throwable? = null,
        ) : ServerError(requestUrl, httpCode, message, cause)
    }

    class ConnectionApiException(
        message: String = "Connection error",
    ) : ApiException(message = message)

    class TimeoutApiException(
        message: String = "Timeout error",
    ) : ApiException(message = message)

    class UnknownApiException(
        httpCode: Int,
        message: String = "Unknown API error. Code: $httpCode",
    ) : ApiException(message = message)
}