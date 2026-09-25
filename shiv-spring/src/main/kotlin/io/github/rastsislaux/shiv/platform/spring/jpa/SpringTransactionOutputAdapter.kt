package io.github.rastsislaux.shiv.platform.spring.jpa

import io.github.rastsislaux.shiv.core.application.TransactionMode
import io.github.rastsislaux.shiv.core.application.TransactionOutputPort
import io.github.rastsislaux.shiv.core.application.diagnostic.DiagnosticEvent
import io.github.rastsislaux.shiv.core.application.diagnostic.DiagnosticOutputPort
import io.github.rastsislaux.shiv.core.platform.PlatformException
import org.springframework.transaction.support.TransactionSynchronizationManager
import org.springframework.transaction.support.TransactionTemplate

class SpringTransactionOutputAdapter(
    private val transactionTemplate: TransactionTemplate,
    private val diagnosticPort: DiagnosticOutputPort,
) : TransactionOutputPort {
    override fun <T> execute(
        mode: TransactionMode,
        block: () -> T
    ): T {
        val isThereATransaction = TransactionSynchronizationManager.isActualTransactionActive()
        if (isThereATransaction) {
            diagnosticPort.report(DiagnosticEvent(
                code = "io.github.rastsislaux.shiv.spring.jpa:TransactionAlreadyExists",
                message = "Transaction already exists",
                severity = DiagnosticEvent.Severity.WARNING,
                cause = TransactionAlreadyExistsException()
            ))
        }

        return transactionTemplate.execute {
            block()
        }
    }

    class TransactionAlreadyExistsException(override val cause: Throwable? = null) :
        PlatformException("Transaction already exists", cause)
}
