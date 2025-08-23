package com.mrenann.dataagrin.core.data.remote.model.weather


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Hourly(
    @SerialName("is_day")
    val isDay: List<Int?>? = listOf(),
    @SerialName("rain")
    val rain: List<Double?>? = listOf(),
    @SerialName("relative_humidity_2m")
    val relativeHumidity2m: List<Int?>? = listOf(),
    @SerialName("temperature_2m")
    val temperature2m: List<Double?>? = listOf(),
    @SerialName("time")
    val time: List<String?>? = listOf()
)