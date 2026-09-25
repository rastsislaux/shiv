package io.github.rastsislaux.shiv.horse.service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = ["io.github.rastsislaux.shiv.horse.service"]
)
class ShivHorseServiceApplication

fun main(args: Array<String>) {
    runApplication<ShivHorseServiceApplication>(*args)
}
