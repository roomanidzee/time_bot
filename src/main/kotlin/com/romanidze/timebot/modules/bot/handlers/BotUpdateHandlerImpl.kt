package com.romanidze.timebot.modules.bot.handlers

import com.cmlteam.telegram_bot_common.BotUpdateHandler
import com.cmlteam.telegram_bot_common.Emoji
import com.cmlteam.telegram_bot_common.TelegramSender
import com.pengrad.telegrambot.model.Message
import com.pengrad.telegrambot.model.Update
import com.romanidze.timebot.modules.bot.enums.BotCommand
import com.romanidze.timebot.modules.bot.enums.BotCommandType
import com.romanidze.timebot.modules.time.dto.RecordedTime
import com.romanidze.timebot.modules.time.services.interfaces.TimeInfoService

class BotUpdateHandlerImpl(
    private val timeInfoService: TimeInfoService
) : BotUpdateHandler {
    override fun processUpdate(sender: TelegramSender, update: Update) {

        val message: Message? = update.message()

        if (message != null) {

            val chatID: Long = message.chat().id()
            val messageText: String = message.text()
            val userID: Long = message.from().id()

            val command: BotCommand? = BotCommand.parse(messageText)

            if (command != null) {

                when (command.type) {

                    BotCommandType.START -> {
                        sender.sendText(chatID, "This is a bot for recording of your time! Let's go!")
                    }

                    BotCommandType.RECORD -> {
                        val inputValues: List<String> = command.commandValues

                        val recordResult: RecordedTime = timeInfoService.recordTime(userID, inputValues)

                        if (recordResult.hoursValue == 0 && recordResult.minutesValue == 0) {
                            sender.sendText(chatID, Emoji.ERROR.msg("Something is wrong with your input! Please, try again"))
                        } else {
                            sender.sendText(chatID, Emoji.SUCCESS.msg("You recorded ${recordResult.hoursValue} hours and ${recordResult.minutesValue} minutes"))
                        }
                    }
                }
            } else {
                sender.sendText(chatID, Emoji.WARN.msg("I don't understand..."))
            }
        }
    }
}
