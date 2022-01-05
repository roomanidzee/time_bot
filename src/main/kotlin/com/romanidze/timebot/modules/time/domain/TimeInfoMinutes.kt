package com.romanidze.timebot.modules.time.domain

data class TimeInfoMinutes(
    var id: Long? = null,
    val userID: Long,
    val minutesValue: Int
)
