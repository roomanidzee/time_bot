package com.romanidze.timebot.modules.bot.enums

import com.romanidze.timebot.application.Application
import com.romanidze.timebot.modules.time.services.interfaces.TimeInfoService
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [Application::class])
class BotCommandTest(val service: TimeInfoService) : WordSpec() {

    init {

        "BotCommand parser" should {

            "parse start command" {

                BotCommand.parse("/start")!!.type shouldBe BotCommandType.START
            }

            "parse record command" {

                BotCommand.parse("/record 4h")!!.type shouldBe BotCommandType.RECORD
            }

            "return null on unknown commands" {

                BotCommand.parse("/help") shouldBe null
            }
        }
    }
}
