package com.mrenann.dataagrin.weather.domain.source

import com.mrenann.dataagrin.core.domain.model.WeatherInfo

interface WeatherRemoteDataSource {
    suspend fun fetchWeather(latitude: Double, longitude: Double): WeatherInfo
}
