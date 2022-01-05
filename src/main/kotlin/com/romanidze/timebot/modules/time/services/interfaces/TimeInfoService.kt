package com.romanidze.timebot.modules.time.services.interfaces

import com.romanidze.timebot.modules.time.dto.RecordedTime

interface TimeInfoService {

    fun recordTime(userID: Long, inputValues: List<String>): RecordedTime
}
