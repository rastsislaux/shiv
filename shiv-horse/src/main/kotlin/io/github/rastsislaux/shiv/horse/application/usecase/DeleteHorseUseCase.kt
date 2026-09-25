package io.github.rastsislaux.shiv.horse.application.usecase

import io.github.rastsislaux.shiv.core.application.ApplicationComponent
import io.github.rastsislaux.shiv.core.application.TransactionOutputPort
import io.github.rastsislaux.shiv.core.application.hex.Command
import io.github.rastsislaux.shiv.core.application.hex.CommandUseCase
import io.github.rastsislaux.shiv.core.application.hex.DeleteOutputPort
import io.github.rastsislaux.shiv.core.application.hex.GetOutputPort
import io.github.rastsislaux.shiv.core.application.hex.NoResult
import io.github.rastsislaux.shiv.core.application.hex.TargetsResource
import io.github.rastsislaux.shiv.horse.application.HorseNotFoundException
import io.github.rastsislaux.shiv.horse.domain.HorseId
import io.github.rastsislaux.shiv.horse.domain.SphericalHorse

interface DeleteHorseUseCase : CommandUseCase<DeleteHorseUseCase.DeleteHorseCommand, NoResult> {
    data class DeleteHorseCommand(
        override val resourceId: HorseId
    ) : Command, TargetsResource<HorseId>
}

@ApplicationComponent
class DeleteHorseUseCaseImpl(
    private val getHorse: GetOutputPort<SphericalHorse, HorseId>,
    private val deleteHorse: DeleteOutputPort<SphericalHorse>,
    private val transactional: TransactionOutputPort,
) : DeleteHorseUseCase {
    override fun execute(input: DeleteHorseUseCase.DeleteHorseCommand): NoResult =
        transactional.execute {
            val horse =
                getHorse.get(input.resourceId) ?: throw HorseNotFoundException(input.resourceId)
            deleteHorse.delete(horse)
            NoResult
        }
}
