package io.github.rastsislaux.shiv.horse.domain

import io.github.rastsislaux.shiv.core.application.Identifiable
import io.github.rastsislaux.shiv.core.domain.AggregateRoot
import java.util.UUID

@JvmInline value class HorseId(val value: UUID)
@JvmInline value class HorseName(val value: String) {
    init {
        require(value.isNotBlank()) { "Horse name cannot be blank" }
    }
}
@JvmInline value class HorseRadius(val meters: Double) {
    init {
        require(meters > 0) { "Radius must be positive" }
        require(meters.isFinite()) { "Radius must be finite" }
    }
}
@JvmInline value class HorseMass(val kilograms: Double) {
    init {
        require(kilograms > 0) { "Mass must be positive" }
        require(kilograms.isFinite()) { "Mass must be finite" }
    }
}
@JvmInline value class MinimumPressure(val pascals: Double) {
    init {
        require(pascals >= 0) { "Vacuum rating must not be negative" }
        require(pascals.isFinite()) { "Vacuum rating must be finite" }
    }
}
enum class HorseStatus {
    AVAILABLE,
    UNAVAILABLE,
    RETIRED,
}

class SphericalHorse private constructor(
    override val id: HorseId,
    name: HorseName,
    radius: HorseRadius,
    mass: HorseMass,
    minimumPressure: MinimumPressure,
    status: HorseStatus,
) : AggregateRoot, Identifiable<HorseId> {
    var name = name
        private set
    var radius = radius
        private set
    var mass = mass
        private set
    var minimumPressure = minimumPressure
        private set
    var status = status
        private set

    fun rename(name: HorseName) {
        ensureNotRetired()
        this.name = name
    }

    fun changeRadius(radius: HorseRadius) {
        ensureNotRetired()
        this.radius = radius
    }

    fun changeMass(mass: HorseMass) {
        ensureNotRetired()
        this.mass = mass
    }

    fun changeMinimumPressure(minimumPressure: MinimumPressure) {
        ensureNotRetired()
        this.minimumPressure = minimumPressure
    }

    fun markUnavailable() {
        ensureNotRetired()
        require(status == HorseStatus.AVAILABLE) { "Horse must be available for this" }
        this.status = HorseStatus.UNAVAILABLE
    }

    fun markAvailable() {
        ensureNotRetired()
        require(status == HorseStatus.UNAVAILABLE) { "Horse must be unavailable for this" }
        this.status = HorseStatus.AVAILABLE
    }

    fun retire() {
        require(status != HorseStatus.RETIRED) { "Horse is already retired" }
        this.status = HorseStatus.RETIRED
    }

    private fun ensureNotRetired() {
        require(status != HorseStatus.RETIRED) { "Horse is retired" }
    }

    companion object {
        fun create(
            name: HorseName,
            radius: HorseRadius,
            mass: HorseMass,
            minimumPressure: MinimumPressure
        ) = SphericalHorse(
            HorseId(UUID.randomUUID()),
            name,
            radius,
            mass,
            minimumPressure,
            HorseStatus.AVAILABLE
        )

        fun rehydrate(
            id: HorseId,
            name: HorseName,
            radius: HorseRadius,
            mass: HorseMass,
            minimumPressure: MinimumPressure,
            status: HorseStatus,
        ) = SphericalHorse(
            id = id,
            name = name,
            radius = radius,
            mass = mass,
            minimumPressure = minimumPressure,
            status = status
        )
    }
}
