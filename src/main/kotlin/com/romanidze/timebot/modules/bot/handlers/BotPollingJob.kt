package com.romanidze.timebot.modules.bot.handlers

import com.cmlteam.telegram_bot_common.LogHelper
import com.cmlteam.telegram_bot_common.TelegramBotWrapper
import com.pengrad.telegrambot.model.Update
import com.pengrad.telegrambot.request.GetUpdates
import org.springframework.scheduling.annotation.Scheduled

class BotPollingJob(
    private val telegramBot: TelegramBotWrapper,
    private val botUpdateHandler: BotUpdateHandlerImpl,
    private val logHelper: LogHelper
) {

    private val getUpdates = GetUpdates()

    @Scheduled(fixedRate = 100)
    fun processUpdates() {
        val updatesResponse = telegramBot.execute(getUpdates)
        if (!updatesResponse.isOk) {
            return
        }
        val updates: List<Update> = updatesResponse.updates()
        for (update in updates) {
            logHelper.captureLogParams(update)

            botUpdateHandler.processUpdate(telegramBot, update)
            getUpdates.offset(update.updateId() + 1)
        }
    }
}
