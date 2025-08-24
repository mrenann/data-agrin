package com.mrenann.dataagrin.core.utils


import androidx.room.TypeConverter
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class LocalTimeConverter {

    private val formatter = DateTimeFormatter.ISO_LOCAL_TIME

    @TypeConverter
    fun fromLocalTime(time: LocalTime): String = time.format(formatter)

    @TypeConverter
    fun toLocalTime(value: String): LocalTime = LocalTime.parse(value, formatter)
}