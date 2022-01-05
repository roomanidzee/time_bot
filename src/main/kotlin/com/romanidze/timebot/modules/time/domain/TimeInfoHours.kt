package com.romanidze.timebot.modules.time.domain

data class TimeInfoHours(
    var id: Long? = null,
    val userID: Long,
    val hoursValue: Int
)
