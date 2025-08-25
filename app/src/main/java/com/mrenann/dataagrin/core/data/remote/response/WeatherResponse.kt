package com.mrenann.dataagrin.core.data.remote.response

import com.mrenann.dataagrin.core.data.remote.model.weather.Current
import com.mrenann.dataagrin.core.data.remote.model.weather.CurrentUnits
import com.mrenann.dataagrin.core.data.remote.model.weather.Hourly
import com.mrenann.dataagrin.core.data.remote.model.weather.HourlyUnits
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("current")
    val current: Current? = Current(),
    @SerialName("current_units")
    val currentUnits: CurrentUnits? = CurrentUnits(),
    @SerialName("elevation")
    val elevation: Double? = 0.0,
    @SerialName("generationtime_ms")
    val generationtimeMs: Double? = 0.0,
    @SerialName("hourly")
    val hourly: Hourly? = Hourly(),
    @SerialName("hourly_units")
    val hourlyUnits: HourlyUnits? = HourlyUnits(),
    @SerialName("latitude")
    val latitude: Double? = 0.0,
    @SerialName("longitude")
    val longitude: Double? = 0.0,
    @SerialName("timezone")
    val timezone: String? = "",
    @SerialName("timezone_abbreviation")
    val timezoneAbbreviation: String? = "",
    @SerialName("utc_offset_seconds")
    val utcOffsetSeconds: Int? = 0
)