package io.github.rastsislaux.shiv.core.application.diagnostic

data class DiagnosticEvent(
    val code: String,
    val message: String,
    val severity: Severity,
    val attributes: Map<String, Any?> = emptyMap(),
    /**
     * Throwable representing the condition that produced this diagnostic.
     * It does not necessarily have to have been thrown.
     */
    val cause: Throwable? = null,
) {
    enum class Severity {
        INFO,
        WARNING,
        ERROR,
    }
}
