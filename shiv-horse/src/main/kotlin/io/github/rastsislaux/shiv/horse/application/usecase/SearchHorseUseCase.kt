package io.github.rastsislaux.shiv.horse.application.usecase

import io.github.rastsislaux.shiv.core.application.ApplicationComponent
import io.github.rastsislaux.shiv.core.application.TransactionOutputPort
import io.github.rastsislaux.shiv.core.application.hex.HexagonalMapper
import io.github.rastsislaux.shiv.core.application.hex.PageResult
import io.github.rastsislaux.shiv.core.application.hex.Query
import io.github.rastsislaux.shiv.core.application.hex.QueryUseCase
import io.github.rastsislaux.shiv.core.application.hex.SearchOutputPort
import io.github.rastsislaux.shiv.horse.application.HorseMapper
import io.github.rastsislaux.shiv.horse.application.HorseResult
import io.github.rastsislaux.shiv.horse.domain.HorseName
import io.github.rastsislaux.shiv.horse.domain.SphericalHorse

interface SearchHorseUseCase :
    QueryUseCase<SearchHorseUseCase.SearchHorseQuery, PageResult<HorseResult>> {
    data class SearchHorseQuery(
        val name: HorseName?,
        val page: Int,
        val size: Int,
    ) : Query
}

@ApplicationComponent
class SearchHorseUseCaseImpl(
    private val searchHorse: SearchOutputPort<SphericalHorse, SearchHorseUseCase.SearchHorseQuery>,
    private val transactional: TransactionOutputPort,
) : SearchHorseUseCase {
    override fun execute(input: SearchHorseUseCase.SearchHorseQuery): PageResult<HorseResult> =
        transactional.execute {
            val horses =
                searchHorse.search(input, SearchOutputPort.PageRequest(input.page, input.size))
            HexagonalMapper.mapToResult(horses, HorseMapper::mapToResult)
        }
}
