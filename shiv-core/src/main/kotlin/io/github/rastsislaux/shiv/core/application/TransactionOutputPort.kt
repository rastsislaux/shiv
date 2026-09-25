package io.github.rastsislaux.shiv.core.application

interface TransactionOutputPort {
    fun <T> execute(mode: TransactionMode = TransactionMode.READ_WRITE, block: () -> T): T
}

enum class TransactionMode {
    READ_ONLY,
    READ_WRITE,
}
