package com.romanidze.timebot.modules.time.services.implementations

import com.romanidze.timebot.modules.time.domain.AnalyticTimeInfo
import com.romanidze.timebot.modules.time.domain.TimeInfoHours
import com.romanidze.timebot.modules.time.domain.TimeInfoHoursAndMinutes
import com.romanidze.timebot.modules.time.domain.TimeInfoMinutes
import com.romanidze.timebot.modules.time.dto.RecordedTime
import com.romanidze.timebot.modules.time.mappers.mybatis.TimeInfoDBMapper
import com.romanidze.timebot.modules.time.services.interfaces.TimeInfoService
import mu.KLogging
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class TimeInfoServiceImpl(private val dbMapper: TimeInfoDBMapper) : TimeInfoService {
    override fun recordTime(userID: Long, inputValues: List<String>): RecordedTime {

        if (inputValues.size > 2 || inputValues.isEmpty()) {
            logger.info("possibly wrong input for bot")
            return RecordedTime(0, 0)
        }

        if (inputValues.size == 1) {
            logger.info("single input element")
            val inputValue: String = inputValues[0]

            return if (inputValue.contains("h") && 'h' == inputValue.toCharArray().last()) {

                try {
                    val hoursValue: Int = inputValue.replace("h", "").toInt()
                    dbMapper.saveHours(TimeInfoHours(userID = userID, hoursValue = hoursValue))
                    RecordedTime(hoursValue, 0)
                } catch (e: Exception) {
                    RecordedTime(0, 0)
                }
            } else if (inputValue.contains("m") && 'm' == inputValue.toCharArray().last()) {

                try {
                    val minutesValue: Int = inputValue.replace("m", "").toInt()
                    dbMapper.saveMinutes(TimeInfoMinutes(userID = userID, minutesValue = minutesValue))
                    RecordedTime(0, minutesValue)
                } catch (e: Exception) {
                    RecordedTime(0, 0)
                }
            } else {
                RecordedTime(0, 0)
            }
        } else {

            logger.info("full time input element")

            val firstValue: String = inputValues[0]
            val secondValue: String = inputValues[1]

            val firstCond: Boolean = firstValue.contains("h") && 'h' == firstValue.toCharArray().last()
            val secondCond: Boolean = secondValue.contains("m") && 'm' == secondValue.toCharArray().last()

            return if (firstCond && secondCond) {

                try {
                    val hoursValue: Int = firstValue.replace("h", "").toInt()
                    val minutesValue: Int = secondValue.replace("m", "").toInt()
                    dbMapper.saveTime(TimeInfoHoursAndMinutes(userID = userID, hoursValue = hoursValue, minutesValue = minutesValue))
                    RecordedTime(hoursValue, minutesValue)
                } catch (e: Exception) {
                    RecordedTime(0, 0)
                }
            } else {
                RecordedTime(0, 0)
            }
        }
    }

    override fun getTodayInfo(userID: Long): AnalyticTimeInfo {

        logger.info("retrieving info for today for user $userID")

        return dbMapper.getTimeForToday(userID)
    }

    override fun getPeriodInfo(inputDate: String, userID: Long): List<AnalyticTimeInfo> {

        val elements: List<String> = inputDate.split("-")

        val firstCond: Boolean = elements.size == 3
        val secondCond: Boolean = elements.all { it.all { ch -> ch.isDigit() } }

        return if (!(firstCond && secondCond)) {
            emptyList()
        } else {
            dbMapper.getTimeForPeriod(inputDate, userID)
        }
    }

    override fun getDayInfo(inputDate: String, userID: Long): AnalyticTimeInfo {

        val elements: List<String> = inputDate.split("-")

        val firstCond: Boolean = elements.size == 3
        val secondCond: Boolean = elements.all { it.all { ch -> ch.isDigit() } }

        return if (!(firstCond && secondCond)) {
            AnalyticTimeInfo(0L, "", "")
        } else {
            dbMapper.getTimeForDay(inputDate, userID)
        }
    }

    companion object : KLogging()
}
