package com.romanidze.timebot.modules.bot.enums

enum class BotCommandType(private val cmd: String, private val hasValues: Boolean) {

    START("start", false),
    RECORD("record", true),
    TODAY("today", false),
    FROM("from", true),
    DATE("date", true);

    fun isCommand(commandCandidate: String): Boolean {
        return if (!hasValues) {
            "/$cmd" == commandCandidate
        } else {
            commandCandidate.startsWith("/$cmd")
        }
    }

    fun getValues(command: String): List<String> {

        return if (hasValues) {
            command.removePrefix("/$cmd ").split(" ")
        } else emptyList()
    }
}
