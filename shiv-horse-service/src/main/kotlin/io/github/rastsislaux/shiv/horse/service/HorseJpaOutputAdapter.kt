package io.github.rastsislaux.shiv.horse.service

import io.github.rastsislaux.shiv.core.application.hex.DeleteOutputPort
import io.github.rastsislaux.shiv.core.application.hex.GetOutputPort
import io.github.rastsislaux.shiv.core.application.hex.SaveOutputPort
import io.github.rastsislaux.shiv.core.application.hex.SearchOutputPort
import io.github.rastsislaux.shiv.horse.application.usecase.SearchHorseUseCase
import io.github.rastsislaux.shiv.horse.domain.HorseId
import io.github.rastsislaux.shiv.horse.domain.HorseMass
import io.github.rastsislaux.shiv.horse.domain.HorseName
import io.github.rastsislaux.shiv.horse.domain.HorseRadius
import io.github.rastsislaux.shiv.horse.domain.HorseStatus
import io.github.rastsislaux.shiv.horse.domain.MinimumPressure
import io.github.rastsislaux.shiv.horse.domain.SphericalHorse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Entity
@Table(name = "spherical_horse")
class SphericalHorseEntity(
    @field:Id
    @field:Column(name = "id", nullable = false)
    internal var id: String = "",
    @field:Column(name = "name", nullable = false)
    internal var name: String = "",
    @field:Column(name = "mass", nullable = false)
    internal var mass: Double = 0.0,
    @field:Column(name = "radius", nullable = false)
    internal var radius: Double = 0.0,
    @field:Column(name = "minimum_pressure", nullable = false)
    internal var minimumPressure: Double = 0.0,
    @Enumerated(EnumType.STRING)
    @field:Column(name = "status", nullable = false)
    internal var status: HorseStatus = HorseStatus.AVAILABLE,
)

interface SphericalHorseRepository : JpaRepository<SphericalHorseEntity, String> {
    fun findAllByNameContaining(name: String, pageable: Pageable): Page<SphericalHorseEntity>
}

@Component
class HorseJpaAdapter(
    private val repository: SphericalHorseRepository,
) : SaveOutputPort<SphericalHorse>,
    GetOutputPort<SphericalHorse, HorseId>,
    SearchOutputPort<SphericalHorse, SearchHorseUseCase.SearchHorseQuery>,
    DeleteOutputPort<SphericalHorse> {
    override fun save(value: SphericalHorse): SphericalHorse {
        val entity = toEntity(value)
        val saved = repository.save(entity)
        return toDomain(saved)
    }

    override fun get(id: HorseId): SphericalHorse? {
        val entity = repository.findById(id.value.toString()).getOrNull()
        return entity?.let { toDomain(it) }
    }

    override fun search(
        query: SearchHorseUseCase.SearchHorseQuery,
        page: SearchOutputPort.PageRequest
    ): SearchOutputPort.Page<SphericalHorse> {
        val nameFilter = query.name?.value
        val pageRequest = PageRequest.of(
            query.page,
            query.size
        )

        val entities = if (nameFilter != null) {
            repository.findAllByNameContaining(nameFilter, pageRequest)
        } else {
            repository.findAll(pageRequest)
        }

        return SearchOutputPort.Page(
            items = entities.content.map { toDomain(it) },
            page = entities.pageable.pageNumber,
            size = entities.pageable.pageSize,
            totalItems = entities.totalElements,
            totalPages = entities.totalPages,
        )
    }

    override fun delete(value: SphericalHorse) {
        repository.deleteById(value.id.value.toString())
    }

    companion object {
        fun toEntity(horse: SphericalHorse) = SphericalHorseEntity(
            id = horse.id.value.toString(),
            name = horse.name.value,
            mass = horse.mass.kilograms,
            radius = horse.radius.meters,
            minimumPressure = horse.minimumPressure.pascals,
            status = horse.status
        )
        
        fun toDomain(horse: SphericalHorseEntity) = SphericalHorse.rehydrate(
            id = HorseId(UUID.fromString(horse.id)),
            name = HorseName(horse.name),
            radius = HorseRadius(horse.radius),
            mass = HorseMass(horse.mass),
            minimumPressure = MinimumPressure(horse.minimumPressure),
            status = horse.status
        )
    }
}