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

interface RetireHorseUseCase : CommandUseCase<RetireHorseUseCase.RetireHorseCommand, HorseResult> {
    data class RetireHorseCommand(override val resourceId: HorseId) : Command,
        TargetsResource<HorseId>
}

@ApplicationComponent
class RetireHorseUseCaseImpl(
    private val getHorse: GetOutputPort<SphericalHorse, HorseId>,
    private val saveHorse: SaveOutputPort<SphericalHorse>,
    private val transactional: TransactionOutputPort,
) : RetireHorseUseCase {
    override fun execute(input: RetireHorseUseCase.RetireHorseCommand): HorseResult =
        transactional.execute {
            val horse =
                getHorse.get(input.resourceId) ?: throw HorseNotFoundException(input.resourceId)
            horse.retire()

            HorseMapper.mapToResult(
                saveHorse.save(horse)
            )
        }
}
