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
import io.github.rastsislaux.shiv.horse.domain.SphericalHorse

interface MarkHorseAvailableUseCase :
    CommandUseCase<MarkHorseAvailableUseCase.MarkHorseAvailableCommand, HorseResult> {
    data class MarkHorseAvailableCommand(
        override val resourceId: HorseId
    ) : Command, TargetsResource<HorseId>
}

@ApplicationComponent
class MarkHorseAvailableUseCaseImpl(
    private val getHorse: GetOutputPort<SphericalHorse, HorseId>,
    private val saveHorse: SaveOutputPort<SphericalHorse>,
    private val transactional: TransactionOutputPort,
) : MarkHorseAvailableUseCase {
    override fun execute(input: MarkHorseAvailableUseCase.MarkHorseAvailableCommand): HorseResult =
        transactional.execute {
            val horse =
                getHorse.get(input.resourceId) ?: throw HorseNotFoundException(input.resourceId)
            horse.markAvailable()

            HorseMapper.mapToResult(
                saveHorse.save(horse)
            )
        }
}
