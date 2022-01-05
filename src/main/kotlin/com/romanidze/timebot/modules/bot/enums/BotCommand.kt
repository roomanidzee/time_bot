package com.romanidze.timebot.modules.bot.enums

class BotCommand(val type: BotCommandType, val commandValues: List<String>) {

    companion object {

        fun parse(commandCandidate: String?): BotCommand? {
            if (commandCandidate == null) return null
            for (type in BotCommandType.values()) {
                if (type.isCommand(commandCandidate)) {
                    return BotCommand(type, type.getValues(commandCandidate))
                }
            }
            return null
        }
    }
}
