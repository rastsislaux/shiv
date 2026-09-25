package io.github.rastsislaux.shiv.horse.service.configuration

import io.github.rastsislaux.shiv.core.application.ApplicationComponent
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
class ApplicationComponentDiscoveryConfiguration