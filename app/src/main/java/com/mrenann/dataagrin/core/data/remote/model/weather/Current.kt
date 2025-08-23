package com.mrenann.dataagrin.core.data.remote.model.weather


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Current(
    @SerialName("interval")
    val interval: Int? = 0,
    @SerialName("is_day")
    val isDay: Int? = 0,
    @SerialName("rain")
    val rain: Double? = 0.0,
    @SerialName("relative_humidity_2m")
    val relativeHumidity2m: Int? = 0,
    @SerialName("temperature_2m")
    val temperature2m: Double? = 0.0,
    @SerialName("time")
    val time: String? = ""
)