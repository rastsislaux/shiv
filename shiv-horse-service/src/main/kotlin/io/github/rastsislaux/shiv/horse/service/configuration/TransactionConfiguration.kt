package io.github.rastsislaux.shiv.horse.service.configuration

import io.github.rastsislaux.shiv.core.application.diagnostic.DiagnosticOutputPort
import io.github.rastsislaux.shiv.platform.spring.jpa.SpringTransactionOutputAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.support.TransactionTemplate

@Configuration
class TransactionConfiguration {

    @Bean
    fun transactionAdapter(
        transactionTemplate: TransactionTemplate,
        diagnosticOutputPort: DiagnosticOutputPort,
    ) = SpringTransactionOutputAdapter(transactionTemplate, diagnosticOutputPort)
}