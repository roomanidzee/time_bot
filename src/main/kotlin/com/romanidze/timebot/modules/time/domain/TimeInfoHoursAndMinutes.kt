package com.romanidze.timebot.modules.time.domain

data class TimeInfoHoursAndMinutes(
    var id: Long? = null,
    val userID: Long,
    val hoursValue: Int,
    val minutesValue: Int
)
