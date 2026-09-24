package io.github.rastsislaux.shiv.horse.service

import io.github.rastsislaux.shiv.core.application.hex.PageResult
import io.github.rastsislaux.shiv.core.application.hex.Result
import io.github.rastsislaux.shiv.horse.application.HorseResult
import io.github.rastsislaux.shiv.horse.application.usecase.CreateHorseUseCase
import io.github.rastsislaux.shiv.horse.application.usecase.DeleteHorseUseCase
import io.github.rastsislaux.shiv.horse.application.usecase.GetHorseUseCase
import io.github.rastsislaux.shiv.horse.application.usecase.MarkHorseAvailableUseCase
import io.github.rastsislaux.shiv.horse.application.usecase.MarkHorseUnavailableUseCase
import io.github.rastsislaux.shiv.horse.application.usecase.RetireHorseUseCase
import io.github.rastsislaux.shiv.horse.application.usecase.SearchHorseUseCase
import io.github.rastsislaux.shiv.horse.application.usecase.UpdateHorseUseCase
import io.github.rastsislaux.shiv.horse.domain.HorseId
import io.github.rastsislaux.shiv.horse.domain.HorseMass
import io.github.rastsislaux.shiv.horse.domain.HorseName
import io.github.rastsislaux.shiv.horse.domain.HorseRadius
import io.github.rastsislaux.shiv.horse.domain.HorseStatus
import io.github.rastsislaux.shiv.horse.domain.MinimumPressure
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/v1/horses")
class HorseRestInputAdapter(
    private val createHorseUseCase: CreateHorseUseCase,
    private val getHorseUseCase: GetHorseUseCase,
    private val searchHorseUseCase: SearchHorseUseCase,
    private val updateHorseUseCase: UpdateHorseUseCase,
    private val deleteHorseUseCase: DeleteHorseUseCase,
    private val markHorseAsAvailableUseCase: MarkHorseAvailableUseCase,
    private val markHorseAsUnavailableUseCase: MarkHorseUnavailableUseCase,
    private val retireHorseUseCase: RetireHorseUseCase,
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createHorse(
        @RequestBody request: CreateHorseRequestDto,
    ): HorseDto {
        val result = createHorseUseCase.execute(CreateHorseUseCase.CreateHorseCommand(
            name = HorseName(request.name),
            radius = HorseRadius(request.radius),
            mass = HorseMass(request.mass),
            minimumPressure = MinimumPressure(request.minimumPressure),
        ))

        return HorseDto.from(result)
    }

    @GetMapping("/{id}")
    fun getHorse(@PathVariable id: String): HorseDto {
        val result = getHorseUseCase.execute(GetHorseUseCase.GetHorseQuery(
            resourceId = HorseId(UUID.fromString(id)),
        ))

        return HorseDto.from(result)
    }

    @PostMapping("/search")
    fun searchHorse(@RequestBody query: SearchHorseUseCase.SearchHorseQuery): PageDto<HorseDto> {
        val result = searchHorseUseCase.execute(query)
        return PageDto.from(result, HorseDto::from)
    }

    @PutMapping("/{id}")
    fun updateHorse(
        @PathVariable id: String,
        @RequestBody request: UpdateHorseRequestDto,
    ): HorseDto {
        val result = updateHorseUseCase.execute(UpdateHorseUseCase.UpdateHorseCommand(
            resourceId = HorseId(UUID.fromString(id)),
            name = HorseName(request.name),
            radius = HorseRadius(request.radius),
            mass = HorseMass(request.mass),
            minimumPressure = MinimumPressure(request.minimumPressure),
        ))

        return HorseDto.from(result)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteHorse(@PathVariable id: String) {
        deleteHorseUseCase.execute(DeleteHorseUseCase.DeleteHorseCommand(
            resourceId = HorseId(UUID.fromString(id))
        ))
    }

    @PostMapping("/{id}/availability")
    fun updateAvailability(
        @PathVariable id: String,
        @RequestBody request: UpdateHorseAvailabilityRequestDto
    ): HorseDto {
        val resourceId = HorseId(UUID.fromString(id))
        val result = if (request.isAvailable) {
            markHorseAsAvailableUseCase.execute(MarkHorseAvailableUseCase.MarkHorseAvailableCommand(
                resourceId = resourceId
            ))
        } else {
            markHorseAsUnavailableUseCase.execute(MarkHorseUnavailableUseCase.MarkHorseUnavailableCommand(
                resourceId = resourceId
            ))
        }
        return HorseDto.from(result)
    }

    @PostMapping("/{id}/retirement")
    fun retireHorse(@PathVariable id: String): HorseDto {
        val result = retireHorseUseCase.execute(RetireHorseUseCase.RetireHorseCommand(
            resourceId = HorseId(UUID.fromString(id))
        ))
        return HorseDto.from(result)
    }
}

data class CreateHorseRequestDto(
    val name: String,
    val radius: Double,
    val mass: Double,
    val minimumPressure: Double,
)

data class UpdateHorseRequestDto(
    val name: String,
    val radius: Double,
    val mass: Double,
    val minimumPressure: Double,
)

data class UpdateHorseAvailabilityRequestDto(
    val isAvailable: Boolean,
)

data class PageDto<T>(
    val items: List<T>,
    val page: Int,
    val size: Int,
    val totalItems: Long,
    val totalPages: Int,
) {
    companion object {
        fun <I : Result, O> from(page: PageResult<I>, itemMapper: (I) -> O): PageDto<O> {
            return PageDto(
                items = page.items.map(itemMapper),
                page = page.page,
                size = page.size,
                totalItems = page.totalItems,
                totalPages = page.totalPages,
            )
        }
    }
}

data class HorseDto(
    val id: String,
    val name: String,
    val radius: Double,
    val mass: Double,
    val minimumPressure: Double,
    val status: HorseStatus,
) {
    companion object {
        fun from(result: HorseResult) = HorseDto(
            id = result.id.value.toString(),
            name = result.name.value,
            radius = result.radius.meters,
            mass = result.mass.kilograms,
            minimumPressure = result.minimumPressure.pascals,
            status = result.status,
        )
    }
}
