package com.mrenann.dataagrin.core.data.remote.api

import com.mrenann.dataagrin.core.data.remote.response.WeatherResponse

interface WeatherApi {
    suspend fun getWeatherData(latitude: Double, longitude: Double): Result<WeatherResponse>
}