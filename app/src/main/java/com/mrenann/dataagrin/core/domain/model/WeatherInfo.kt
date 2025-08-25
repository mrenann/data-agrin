package com.mrenann.dataagrin.core.domain.model

data class WeatherInfo(
    val current: Weather,
    val hourly: List<HourlyForecast>
)