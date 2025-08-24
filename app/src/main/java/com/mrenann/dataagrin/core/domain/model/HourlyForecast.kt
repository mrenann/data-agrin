package com.mrenann.dataagrin.core.domain.model

data class HourlyForecast(
    val time: String,
    val temperature: Double,
    val humidity: Int,
    val rain: Double,
    val isDay: Boolean
)
