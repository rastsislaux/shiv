package io.github.rastsislaux.shiv.horse.application

import io.github.rastsislaux.shiv.horse.domain.SphericalHorse

object HorseMapper {
    fun mapToResult(horse: SphericalHorse) = HorseResult(
        id = horse.id,
        name = horse.name,
        mass = horse.mass,
        radius = horse.radius,
        minimumPressure = horse.minimumPressure,
        status = horse.status,
    )
}