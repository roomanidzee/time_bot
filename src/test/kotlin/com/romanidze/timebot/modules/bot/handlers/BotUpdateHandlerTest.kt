package com.romanidze.timebot.modules.bot.handlers

import com.cmlteam.telegram_bot_common.test.BotReply
import com.cmlteam.telegram_bot_common.test.BotTester
import com.cmlteam.telegram_bot_common.test.TelegramFactory
import com.romanidze.timebot.application.Application
import com.romanidze.timebot.modules.time.services.interfaces.TimeInfoService
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SpringBootTest(classes = [Application::class])
class BotUpdateHandlerTest(val service: TimeInfoService) : WordSpec() {

    init {

        val telegramFactory = TelegramFactory()

        val botTester =
            BotTester(
                BotUpdateHandlerImpl(
                    service
                )
            )

        val user = telegramFactory.user(1, "John", "Doe")

        "TimeBot" should {

            "process /start command" {

                val responseText: BotReply = botTester.processUserText(user, "/start")

                responseText.text shouldBe "This is a bot for recording of your time! Let's go!"
            }

            "process /record command for hours" {
                val responseText: BotReply = botTester.processUserText(user, "/record 4h")

                responseText.text shouldContain "You"
            }

            "process /record command for minutes" {
                val responseText: BotReply = botTester.processUserText(user, "/record 4m")

                responseText.text shouldContain "You"
            }

            "process /record command for full input time" {

                val responseText: BotReply = botTester.processUserText(user, "/record 4h 4m")

                responseText.text shouldContain "You"
            }

            "not process /record for wrong input" {
                val responseText: BotReply = botTester.processUserText(user, "/record gm")

                responseText.text shouldContain "Something"
            }

            "process /today command" {

                val responseText: BotReply = botTester.processUserText(user, "/today")

                responseText.text shouldContain "You"
            }

            "process /from command" {

                val currentDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

                val responseText: BotReply = botTester.processUserText(user, "/from $currentDate")

                responseText.text shouldContain "Report"
            }

            "process /date command" {

                val currentDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

                val responseText: BotReply = botTester.processUserText(user, "/date $currentDate")

                responseText.text shouldContain "You"
            }

            "send warn message for unknown command" {

                val responseText: BotReply = botTester.processUserText(user, "/help")

                responseText.text shouldContain "I don't understand..."
            }
        }
    }
}
