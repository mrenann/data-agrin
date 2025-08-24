package com.mrenann.dataagrin.core.utils

import androidx.room.TypeConverter
import com.mrenann.dataagrin.core.data.local.entity.HourlyForecastEntity
import kotlinx.serialization.json.Json

class Converters {

    @TypeConverter
    fun fromHourlyList(value: List<HourlyForecastEntity>): String =
        Json.encodeToString(value)

    @TypeConverter
    fun toHourlyList(value: String): List<HourlyForecastEntity> =
        Json.decodeFromString(value)
}
