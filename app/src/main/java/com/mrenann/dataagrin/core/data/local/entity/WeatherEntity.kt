package com.mrenann.dataagrin.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mrenann.dataagrin.core.utils.Converters
import kotlinx.serialization.Serializable

@Entity(tableName = "weather")
@TypeConverters(Converters::class)
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val temperature: Double,
    val humidity: Int,
    val rain: Double,
    val isDay: Boolean,
    val condition: String,
    val city: String,
    val updatedAt: Long,
    val hourly: List<HourlyForecastEntity>
)

@Serializable
data class HourlyForecastEntity(
    val time: String,
    val temperature: Double,
    val humidity: Int,
    val rain: Double,
    val isDay: Boolean
)
