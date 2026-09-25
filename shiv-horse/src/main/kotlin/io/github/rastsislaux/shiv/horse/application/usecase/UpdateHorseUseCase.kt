package io.github.rastsislaux.shiv.horse.application.usecase

import io.github.rastsislaux.shiv.core.application.ApplicationComponent
import io.github.rastsislaux.shiv.core.application.TransactionOutputPort
import io.github.rastsislaux.shiv.core.application.hex.Command
import io.github.rastsislaux.shiv.core.application.hex.CommandUseCase
import io.github.rastsislaux.shiv.core.application.hex.GetOutputPort
import io.github.rastsislaux.shiv.core.application.hex.SaveOutputPort
import io.github.rastsislaux.shiv.core.application.hex.TargetsResource
import io.github.rastsislaux.shiv.horse.application.HorseMapper
import io.github.rastsislaux.shiv.horse.application.HorseNotFoundException
import io.github.rastsislaux.shiv.horse.application.HorseResult
import io.github.rastsislaux.shiv.horse.domain.HorseId
import io.github.rastsislaux.shiv.horse.domain.HorseMass
import io.github.rastsislaux.shiv.horse.domain.HorseName
import io.github.rastsislaux.shiv.horse.domain.HorseRadius
import io.github.rastsislaux.shiv.horse.domain.MinimumPressure
import io.github.rastsislaux.shiv.horse.domain.SphericalHorse

interface UpdateHorseUseCase : CommandUseCase<UpdateHorseUseCase.UpdateHorseCommand, HorseResult> {
    data class UpdateHorseCommand(
        override val resourceId: HorseId,
        val name: HorseName,
        val radius: HorseRadius,
        val mass: HorseMass,
        val minimumPressure: MinimumPressure
    ) : Command, TargetsResource<HorseId>
}

@ApplicationComponent
class UpdateHorseUseCaseImpl(
    private val getHorse: GetOutputPort<SphericalHorse, HorseId>,
    private val saveHorse: SaveOutputPort<SphericalHorse>,
    private val transactional: TransactionOutputPort,
) : UpdateHorseUseCase {
    override fun execute(input: UpdateHorseUseCase.UpdateHorseCommand): HorseResult =
        transactional.execute {
            val horse =
                getHorse.get(input.resourceId) ?: throw HorseNotFoundException(input.resourceId)
            horse.rename(input.name)
            horse.changeMass(input.mass)
            horse.changeRadius(input.radius)
            horse.changeMinimumPressure(input.minimumPressure)

            HorseMapper.mapToResult(
                saveHorse.save(horse)
            )
        }
}
