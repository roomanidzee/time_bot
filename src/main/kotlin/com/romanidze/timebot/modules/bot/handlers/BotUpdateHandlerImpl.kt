package com.romanidze.timebot.modules.bot.handlers

import com.cmlteam.telegram_bot_common.BotUpdateHandler
import com.cmlteam.telegram_bot_common.Emoji
import com.cmlteam.telegram_bot_common.TelegramSender
import com.pengrad.telegrambot.model.Message
import com.pengrad.telegrambot.model.Update
import com.romanidze.timebot.modules.bot.enums.BotCommand
import com.romanidze.timebot.modules.bot.enums.BotCommandType
import com.romanidze.timebot.modules.time.domain.AnalyticTimeInfo
import com.romanidze.timebot.modules.time.dto.RecordedTime
import com.romanidze.timebot.modules.time.services.interfaces.TimeInfoService
import mu.KLogging

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
                        logger.info("processing /start command")
                        sender.sendText(chatID, "This is a bot for recording of your time! Let's go!")
                    }

                    BotCommandType.RECORD -> {
                        logger.info("processing /record command")
                        val inputValues: List<String> = command.commandValues

                        val recordResult: RecordedTime = timeInfoService.recordTime(userID, inputValues)

                        if (recordResult.hoursValue == 0 && recordResult.minutesValue == 0) {
                            sender.sendText(chatID, Emoji.ERROR.msg("Something is wrong with your input! Please, try again"))
                        } else {
                            sender.sendText(chatID, Emoji.SUCCESS.msg("You recorded ${recordResult.hoursValue} hours and ${recordResult.minutesValue} minutes"))
                        }
                    }

                    BotCommandType.TODAY -> {

                        logger.info("processing /today command")

                        val result: AnalyticTimeInfo = timeInfoService.getTodayInfo(userID)

                        sender.sendText(chatID, Emoji.SUCCESS.msg("You recorded for ${result.dateInfo} : ${result.timeInfo}"))
                    }

                    BotCommandType.FROM -> {

                        logger.info("processing /from command")

                        val inputValues: List<String> = command.commandValues

                        if (inputValues.size > 1 || inputValues.isEmpty()) {
                            sender.sendText(chatID, Emoji.ERROR.msg("Something is wrong with your input! Please, try again"))
                        } else {

                            val result: List<AnalyticTimeInfo> = timeInfoService.getPeriodInfo(inputValues[0], userID)

                            if (result.isEmpty()) {
                                sender.sendText(chatID, Emoji.ERROR.msg("Something is wrong with your input! Please, try again"))
                            } else {

                                val messages: List<String> = result.map {
                                    "You recorded for ${it.dateInfo} : ${it.timeInfo}"
                                }

                                sender.sendText(chatID, Emoji.SUCCESS.msg("Report: \n ${messages.joinToString(separator = "\n")}"))
                            }
                        }
                    }

                    BotCommandType.DATE -> {

                        logger.info("processing /date command")

                        val inputValues: List<String> = command.commandValues

                        if (inputValues.size > 1 || inputValues.isEmpty()) {
                            sender.sendText(chatID, Emoji.ERROR.msg("Something is wrong with your input! Please, try again"))
                        } else {

                            val result: AnalyticTimeInfo = timeInfoService.getDayInfo(inputValues[0], userID)

                            if (result.userID == 0L) {
                                sender.sendText(chatID, Emoji.ERROR.msg("Something is wrong with your input! Please, try again"))
                            } else {
                                sender.sendText(chatID, Emoji.SUCCESS.msg("You recorded for ${result.dateInfo} : ${result.timeInfo}"))
                            }
                        }
                    }
                }
            } else {
                sender.sendText(chatID, Emoji.WARN.msg("I don't understand..."))
            }
        }
    }

    companion object : KLogging()
}
