package com.mrenann.dataagrin.core.domain.model

data class Weather(
    val temperature: Double,
    val humidity: Int,
    val rain: Double,
    val isDay: Boolean,
    val code: Int
)
