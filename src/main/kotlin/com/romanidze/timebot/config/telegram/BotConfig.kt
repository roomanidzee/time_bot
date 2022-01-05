package com.romanidze.timebot.config.telegram

import com.cmlteam.telegram_bot_common.ErrorReporter
import com.cmlteam.telegram_bot_common.JsonHelper
import com.cmlteam.telegram_bot_common.LogHelper
import com.cmlteam.telegram_bot_common.TelegramBotWrapper
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.request.GetMe
import com.romanidze.timebot.modules.bot.handlers.BotPollingJob
import com.romanidze.timebot.modules.bot.handlers.BotUpdateHandlerImpl
import com.romanidze.timebot.modules.time.services.interfaces.TimeInfoService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BotConfig {

    @Bean
    fun getTelegramBot(botProperties: BotProperties): TelegramBot {
        val telegramBot = TelegramBot(botProperties.token)
        val response = telegramBot.execute(GetMe())
        requireNotNull(response.user()) { "bot token is incorrect" }
        return telegramBot
    }

    @Bean
    fun errorReporter(
        telegramBot: TelegramBot,
        jsonHelper: JsonHelper,
        botProperties: BotProperties
    ): ErrorReporter {
        return ErrorReporter(telegramBot, jsonHelper, listOf(botProperties.adminUser))
    }

    @Bean
    fun telegramBotWrapper(
        telegramBot: TelegramBot,
        jsonHelper: JsonHelper,
        errorReporter: ErrorReporter
    ): TelegramBotWrapper {
        return TelegramBotWrapper(telegramBot, jsonHelper, errorReporter)
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
        telegramBotWrapper: TelegramBotWrapper,
        logHelper: LogHelper,
    ): BotPollingJob {
        return BotPollingJob(telegramBotWrapper, botUpdateHandler, logHelper)
    }
}
