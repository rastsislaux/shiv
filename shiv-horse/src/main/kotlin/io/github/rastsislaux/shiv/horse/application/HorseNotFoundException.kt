package io.github.rastsislaux.shiv.horse.application

import io.github.rastsislaux.shiv.core.application.exception.ResourceNotFoundException
import io.github.rastsislaux.shiv.horse.domain.HorseId

class HorseNotFoundException(
    resourceId: HorseId,
) : ResourceNotFoundException("Horse", resourceId.value)