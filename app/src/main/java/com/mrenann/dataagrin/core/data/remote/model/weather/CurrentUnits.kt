package com.mrenann.dataagrin.core.data.remote.model.weather


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentUnits(
    @SerialName("interval")
    val interval: String? = "",
    @SerialName("is_day")
    val isDay: String? = "",
    @SerialName("rain")
    val rain: String? = "",
    @SerialName("relative_humidity_2m")
    val relativeHumidity2m: String? = "",
    @SerialName("temperature_2m")
    val temperature2m: String? = "",
    @SerialName("time")
    val time: String? = ""
)