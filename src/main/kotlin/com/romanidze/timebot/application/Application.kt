package com.romanidze.timebot.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

/**
 * Main class for launching full system
 *
 * This class works as an entrypoint for all of the modules in system
 */
@SpringBootApplication
@ComponentScan(
    basePackages =
    [
        "com.romanidze.timebot.config",
        "com.romanidze.timebot.modules"
    ]
)
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
