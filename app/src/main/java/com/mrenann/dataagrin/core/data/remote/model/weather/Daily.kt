package com.mrenann.dataagrin.core.data.remote.model.weather


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Daily(
    @SerialName("temperature_2m_max")
    val temperature2mMax: List<Double?>? = listOf(),
    @SerialName("temperature_2m_min")
    val temperature2mMin: List<Double?>? = listOf(),
    @SerialName("time")
    val time: List<String?>? = listOf()
)