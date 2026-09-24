package io.github.rastsislaux.shiv.horse.application

import io.github.rastsislaux.shiv.core.application.hex.Result
import io.github.rastsislaux.shiv.horse.domain.HorseId
import io.github.rastsislaux.shiv.horse.domain.HorseMass
import io.github.rastsislaux.shiv.horse.domain.HorseName
import io.github.rastsislaux.shiv.horse.domain.HorseRadius
import io.github.rastsislaux.shiv.horse.domain.HorseStatus
import io.github.rastsislaux.shiv.horse.domain.MinimumPressure

data class HorseResult(
    val id: HorseId,
    val name: HorseName,
    val mass: HorseMass,
    val radius: HorseRadius,
    val minimumPressure: MinimumPressure,
    val status: HorseStatus,
) : Result