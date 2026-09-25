package io.github.rastsislaux.shiv.core.platform

open class PlatformException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)
