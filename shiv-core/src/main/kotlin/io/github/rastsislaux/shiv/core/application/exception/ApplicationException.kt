package io.github.rastsislaux.shiv.core.application.exception

open class ApplicationException(
    message: String,
    cause: Throwable? = null,
) : RuntimeException(message, cause)

open class ResourceNotFoundException(
    val resourceType: String,
    val resourceId: Any,
    message: String = "$resourceType with id '$resourceId' was not found",
) : ApplicationException(message)

open class ConflictException(
    message: String,
) : ApplicationException(message)

open class ForbiddenException(
    message: String,
) : ApplicationException(message)

open class ValidationException(
    message: String,
) : ApplicationException(message)
