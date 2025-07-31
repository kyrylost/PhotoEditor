package dev.stukalo.common.core.exception

/**
 * Base class for custom exceptions in the application.
 *
 * This class extends [Exception] and allows you to pass an optional message and cause.
 * It's intended to be the base class for other application-specific exceptions.
 *
 * @param message The detail message for this exception, or `null` if none.
 * @param cause The cause of this exception, or `null` if none.
 */
open class AppException(
    message: String? = null,
    cause: Throwable? = null,
) : Exception(message, cause)
