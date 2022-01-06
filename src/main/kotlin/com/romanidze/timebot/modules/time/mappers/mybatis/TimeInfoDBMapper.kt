package com.romanidze.timebot.modules.time.mappers.mybatis

import com.romanidze.timebot.modules.time.domain.AnalyticTimeInfo
import com.romanidze.timebot.modules.time.domain.TimeInfoHours
import com.romanidze.timebot.modules.time.domain.TimeInfoHoursAndMinutes
import com.romanidze.timebot.modules.time.domain.TimeInfoMinutes
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Result
import org.apache.ibatis.annotations.Results
import org.apache.ibatis.annotations.Select

@Mapper
interface TimeInfoDBMapper {

    @Insert(
        "INSERT INTO time_info(user_id, date_info, time_info) " +
            "VALUES (#{userID}, CURRENT_DATE, #{hoursValue}::double precision * '1 hour'::interval)"
    )
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    fun saveHours(timeInfo: TimeInfoHours)

    @Insert(
        "INSERT INTO time_info(user_id, date_info, time_info) " +
            "VALUES (#{userID}, CURRENT_DATE, #{minutesValue}::double precision * '1 minute'::interval)"
    )
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    fun saveMinutes(timeInfo: TimeInfoMinutes)

    @Insert(
        "INSERT INTO time_info(user_id, date_info, time_info) " +
            "VALUES (#{userID}, CURRENT_DATE, #{hoursValue}::double precision * '1 hour'::interval + #{minutesValue}::double precision * '1 minute'::interval)"
    )
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    fun saveTime(timeInfo: TimeInfoHoursAndMinutes)

    @Select("SELECT user_id, date_str, time_sum FROM time_analytics WHERE date_info = CURRENT_DATE AND user_id = #{userID}")
    @Results(
        Result(column = "user_id", property = "userID"),
        Result(column = "date_str", property = "dateInfo"),
        Result(column = "time_sum", property = "timeInfo")
    )
    fun getTimeForToday(@Param("userID") userID: Long): AnalyticTimeInfo

    @Select("SELECT user_id, date_str, time_sum FROM time_analytics WHERE date_info >= #{inputDate}::date AND user_id = #{userID}")
    @Results(
        Result(column = "user_id", property = "userID"),
        Result(column = "date_str", property = "dateInfo"),
        Result(column = "time_sum", property = "timeInfo")
    )
    fun getTimeForPeriod(@Param("inputDate") inputDate: String, @Param("userID") userID: Long): List<AnalyticTimeInfo>

    @Select("SELECT user_id, date_str, time_sum FROM time_analytics WHERE date_str = #{inputDate} AND user_id = #{userID}")
    @Results(
        Result(column = "user_id", property = "userID"),
        Result(column = "date_str", property = "dateInfo"),
        Result(column = "time_sum", property = "timeInfo")
    )
    fun getTimeForDay(@Param("inputDate") inputDate: String, @Param("userID") userID: Long): AnalyticTimeInfo
}
