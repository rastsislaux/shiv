package io.github.rastsislaux.shiv.horse.service

import io.github.rastsislaux.shiv.core.application.ApplicationComponent
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType

@Configuration
@ComponentScan(
    basePackages = [
        "io.github.rastsislaux.shiv.horse.application"
    ],
    includeFilters = [
        ComponentScan.Filter(
            type = FilterType.ANNOTATION,
            classes = [ApplicationComponent::class]
        )
    ],
    useDefaultFilters = false
)
class ShivApplicationComponentConfiguration

@SpringBootApplication(
    scanBasePackages = ["io.github.rastsislaux.shiv.horse.service"]
)
class ShivHorseServiceApplication

fun main(args: Array<String>) {
    runApplication<ShivHorseServiceApplication>(*args)
}
