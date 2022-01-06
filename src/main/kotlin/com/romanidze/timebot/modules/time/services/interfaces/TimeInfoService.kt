package com.romanidze.timebot.modules.time.services.interfaces

import com.romanidze.timebot.modules.time.domain.AnalyticTimeInfo
import com.romanidze.timebot.modules.time.dto.RecordedTime

interface TimeInfoService {

    fun recordTime(userID: Long, inputValues: List<String>): RecordedTime
    fun getTodayInfo(userID: Long): AnalyticTimeInfo
    fun getPeriodInfo(inputDate: String, userID: Long): List<AnalyticTimeInfo>
    fun getDayInfo(inputDate: String, userID: Long): AnalyticTimeInfo
}
