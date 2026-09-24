package io.github.rastsislaux.shiv.horse.application.usecase

import io.github.rastsislaux.shiv.core.application.ApplicationComponent
import io.github.rastsislaux.shiv.core.application.hex.GetOutputPort
import io.github.rastsislaux.shiv.core.application.hex.Query
import io.github.rastsislaux.shiv.core.application.hex.QueryUseCase
import io.github.rastsislaux.shiv.core.application.hex.TargetsResource
import io.github.rastsislaux.shiv.horse.application.HorseMapper
import io.github.rastsislaux.shiv.horse.application.HorseNotFoundException
import io.github.rastsislaux.shiv.horse.application.HorseResult
import io.github.rastsislaux.shiv.horse.domain.HorseId
import io.github.rastsislaux.shiv.horse.domain.SphericalHorse

interface GetHorseUseCase : QueryUseCase<GetHorseUseCase.GetHorseQuery, HorseResult> {
    data class GetHorseQuery(override val resourceId: HorseId) : Query, TargetsResource<HorseId>
}

@ApplicationComponent
class GetHorseUseCaseImpl(
    private val getHorse: GetOutputPort<SphericalHorse, HorseId>
) : GetHorseUseCase {
    override fun execute(input: GetHorseUseCase.GetHorseQuery): HorseResult {
        val horse = getHorse.get(input.resourceId) ?: throw HorseNotFoundException(input.resourceId)
        return HorseMapper.mapToResult(horse)
    }
}

