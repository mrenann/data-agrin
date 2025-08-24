package com.mrenann.dataagrin.weather.domain.source

import com.mrenann.dataagrin.core.domain.model.WeatherInfo

interface WeatherLocalDataSource {
    suspend fun getWeather(): WeatherInfo?
    suspend fun saveWeather(weather: WeatherInfo)
}