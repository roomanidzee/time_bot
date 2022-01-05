package com.romanidze.timebot.modules.time.services

import com.romanidze.timebot.application.Application
import com.romanidze.timebot.modules.time.services.interfaces.TimeInfoService
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [Application::class])
class TimeInfoServiceTest(val service: TimeInfoService) : WordSpec() {

    init {

        "TimeInfoService" should {

            "save record with hours" {

                val inputStr = "4h"
                val recordResult = service.recordTime(1L, listOf(inputStr))

                recordResult.hoursValue shouldBe 4
            }

            "save record with minutes" {
                val inputStr = "4m"
                val recordResult = service.recordTime(1L, listOf(inputStr))

                recordResult.minutesValue shouldBe 4
            }

            "save record with hours and minutes" {

                val inputValues = listOf("4h", "4m")
                val recordResult = service.recordTime(1L, inputValues)

                recordResult.hoursValue shouldBe 4
                recordResult.minutesValue shouldBe 4
            }

            "return empty record in case of errors" {

                val failedResults = listOf(
                    service.recordTime(1L, emptyList()),
                    service.recordTime(1L, listOf("1", "2", "3")),
                    service.recordTime(1L, listOf("1g")),
                    service.recordTime(1L, listOf("gm"))
                )

                failedResults.forEach {
                    it.hoursValue shouldBe 0
                    it.minutesValue shouldBe 0
                }
            }
        }
    }
}
