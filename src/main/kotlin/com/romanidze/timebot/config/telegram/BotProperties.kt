package com.romanidze.timebot.config.telegram

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

@ConfigurationProperties(prefix = "telegram-bot")
@ConstructorBinding
@Validated
data class BotProperties(
    @field:Positive val adminUser: Long = 0,
    @field:NotBlank val token: String? = null,
    @field:NotBlank val backupFolder: String? = null,
    @field:NotBlank val pollTime: String? = null
)
