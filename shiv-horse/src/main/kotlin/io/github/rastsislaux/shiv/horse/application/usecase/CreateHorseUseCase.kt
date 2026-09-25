package io.github.rastsislaux.shiv.horse.application.usecase

import io.github.rastsislaux.shiv.core.application.ApplicationComponent
import io.github.rastsislaux.shiv.core.application.TransactionOutputPort
import io.github.rastsislaux.shiv.core.application.hex.Command
import io.github.rastsislaux.shiv.core.application.hex.CommandUseCase
import io.github.rastsislaux.shiv.core.application.hex.SaveOutputPort
import io.github.rastsislaux.shiv.horse.application.HorseMapper
import io.github.rastsislaux.shiv.horse.application.HorseResult
import io.github.rastsislaux.shiv.horse.domain.HorseMass
import io.github.rastsislaux.shiv.horse.domain.HorseName
import io.github.rastsislaux.shiv.horse.domain.HorseRadius
import io.github.rastsislaux.shiv.horse.domain.MinimumPressure
import io.github.rastsislaux.shiv.horse.domain.SphericalHorse

interface CreateHorseUseCase : CommandUseCase<CreateHorseUseCase.CreateHorseCommand, HorseResult> {
    data class CreateHorseCommand(
        val name: HorseName,
        val radius: HorseRadius,
        val mass: HorseMass,
        val minimumPressure: MinimumPressure,
    ) : Command
}

@ApplicationComponent
class CreateHorseUseCaseImpl(
    private val saveOutputPort: SaveOutputPort<SphericalHorse>,
    private val transactional: TransactionOutputPort,
) : CreateHorseUseCase {
    override fun execute(input: CreateHorseUseCase.CreateHorseCommand): HorseResult =
        transactional.execute {
            val horse = SphericalHorse.create(
                name = input.name,
                radius = input.radius,
                mass = input.mass,
                minimumPressure = input.minimumPressure,
            )

            HorseMapper.mapToResult(
                saveOutputPort.save(horse)
            )
        }
}
