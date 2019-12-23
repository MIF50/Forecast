package com.mif50.forecast.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter


object LocalDateConverter {

    @TypeConverter
    @JvmStatic
    fun stringToDate(str: String?) = str?.let { LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE) }

    @TypeConverter
    @JvmStatic
    fun dateToString(dateTime: LocalDate?) = dateTime?.format(DateTimeFormatter.ISO_LOCAL_DATE)

    @TypeConverter
    @JvmStatic
    fun stringToArraySting(str: String?):List<String> {
       val objects = Gson().fromJson(str, Array<String>::class.java) as Array<String>
        return objects.toList()
    }

    @TypeConverter
    @JvmStatic
    fun arrayStringToString(list: List<String>): String{
        return Gson().toJson(list)
    }

}