package com.romanidze.timebot.modules.time.services.implementations

import com.romanidze.timebot.modules.time.domain.TimeInfoHours
import com.romanidze.timebot.modules.time.domain.TimeInfoHoursAndMinutes
import com.romanidze.timebot.modules.time.domain.TimeInfoMinutes
import com.romanidze.timebot.modules.time.dto.RecordedTime
import com.romanidze.timebot.modules.time.mappers.mybatis.TimeInfoDBMapper
import com.romanidze.timebot.modules.time.services.interfaces.TimeInfoService
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class TimeInfoServiceImpl(private val dbMapper: TimeInfoDBMapper) : TimeInfoService {
    override fun recordTime(userID: Long, inputValues: List<String>): RecordedTime {

        if (inputValues.size > 2 || inputValues.isEmpty()) {
            return RecordedTime(0, 0)
        }

        if (inputValues.size == 1) {

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
}
