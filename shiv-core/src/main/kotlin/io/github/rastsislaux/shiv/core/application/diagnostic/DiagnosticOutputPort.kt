package io.github.rastsislaux.shiv.core.application.diagnostic

import io.github.rastsislaux.shiv.core.application.hex.OutputPort

interface DiagnosticOutputPort : OutputPort {
    fun report(event: DiagnosticEvent)
}