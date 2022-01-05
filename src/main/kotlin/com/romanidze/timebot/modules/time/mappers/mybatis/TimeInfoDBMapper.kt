package com.romanidze.timebot.modules.time.mappers.mybatis

import com.romanidze.timebot.modules.time.domain.TimeInfoHours
import com.romanidze.timebot.modules.time.domain.TimeInfoHoursAndMinutes
import com.romanidze.timebot.modules.time.domain.TimeInfoMinutes
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Options

@Mapper
interface TimeInfoDBMapper {

    @Insert(
        "INSERT INTO time_info(user_id, date_info, time_info) " +
            "VALUES (#{userID}, CURRENT_DATE, #{hoursValue}::double precision * '1 hour'::interval)"
    )
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    fun save(timeInfo: TimeInfoHours)

    @Insert(
        "INSERT INTO time_info(user_id, date_info, time_info) " +
            "VALUES (#{userID}, CURRENT_DATE, #{minutesValue}::double precision * '1 minute'::interval)"
    )
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    fun save(timeInfo: TimeInfoMinutes)

    @Insert(
        "INSERT INTO time_info(user_id, date_info, time_info) " +
            "VALUES (#{userID}, CURRENT_DATE, #{hoursValue}::double precision * '1 hour'::interval + #{minutesValue}::double precision * '1 minute'::interval)"
    )
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    fun save(timeInfo: TimeInfoHoursAndMinutes)
}
