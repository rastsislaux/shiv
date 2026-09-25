package io.github.rastsislaux.shiv.horse.service.configuration

import io.github.rastsislaux.shiv.core.application.diagnostic.DiagnosticEvent
import io.github.rastsislaux.shiv.core.application.diagnostic.DiagnosticOutputPort
import io.github.rastsislaux.shiv.horse.service.logger
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DiagnosticConfiguration {

    @Bean
    fun loggingDiagnosticAdapter(): DiagnosticOutputPort = LoggingDiagnosticAdapter()
}

class LoggingDiagnosticAdapter : DiagnosticOutputPort {
    override fun report(event: DiagnosticEvent) {
        val logger = resolveLogger(event)
        when (event.severity) {
            DiagnosticEvent.Severity.INFO -> logger.info("[${event.code}] ${event.message}", event.cause)
            DiagnosticEvent.Severity.WARNING -> logger.warn("[${event.code}] ${event.message}", event.cause)
            DiagnosticEvent.Severity.ERROR -> logger.error("[${event.code}] ${event.message}", event.cause)
        }
    }

    private fun resolveLogger(event: DiagnosticEvent): Logger {
        val logger = event
            .cause
            ?.stackTrace
            ?.firstOrNull()
            ?.className
            ?.let { LoggerFactory.getLogger(it) }
            ?: this.logger
        return logger
    }
}
