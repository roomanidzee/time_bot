package com.romanidze.timebot.config.telegram

import com.cmlteam.telegram_bot_common.ErrorReporter
import com.cmlteam.telegram_bot_common.JsonHelper
import com.cmlteam.telegram_bot_common.LogHelper
import com.cmlteam.telegram_bot_common.TelegramBotWrapper
import com.pengrad.telegrambot.TelegramBot
import com.romanidze.timebot.modules.bot.handlers.BotPollingJob
import com.romanidze.timebot.modules.bot.handlers.BotUpdateHandlerImpl
import com.romanidze.timebot.modules.time.services.interfaces.TimeInfoService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BotConfig {

    @Bean
    fun getTelegramBot(botProperties: BotProperties): TelegramBot {
        return TelegramBot(botProperties.token)
    }

    @Bean
    fun errorReporter(
        telegramBot: TelegramBot,
        botProperties: BotProperties
    ): ErrorReporter {
        return ErrorReporter(telegramBot, JsonHelper(), listOf(botProperties.adminUser))
    }

    @Bean
    fun telegramBotWrapper(
        telegramBot: TelegramBot,
        errorReporter: ErrorReporter
    ): TelegramBotWrapper {
        return TelegramBotWrapper(telegramBot, JsonHelper(), errorReporter)
    }

    @Bean
    fun botUpdateHandler(
        timeInfoService: TimeInfoService
    ): BotUpdateHandlerImpl {
        return BotUpdateHandlerImpl(
            timeInfoService
        )
    }

    @Bean
    fun botPollingJob(
        botUpdateHandler: BotUpdateHandlerImpl,
        telegramBotWrapper: TelegramBotWrapper
    ): BotPollingJob {
        return BotPollingJob(telegramBotWrapper, botUpdateHandler, LogHelper())
    }
}
