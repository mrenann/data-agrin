package com.mrenann.dataagrin.core.data.remote.api

import com.mrenann.dataagrin.BuildConfig
import com.mrenann.dataagrin.core.data.remote.response.WeatherResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class WeatherApiImpl(private val client: HttpClient) : WeatherApi {

    override suspend fun getWeatherData(latitude: Double, longitude: Double): WeatherResponse {
        return client.get(BuildConfig.BASE_URL) {
            parameter("latitude", latitude)
            parameter("longitude", longitude)
            parameter("daily", "temperature_2m_max,temperature_2m_min")
            parameter("hourly", "temperature_2m,relative_humidity_2m,is_day,rain")
            parameter("current", "temperature_2m,relative_humidity_2m,rain,is_day")
            parameter("timezone", "America/Sao_Paulo")
            parameter("forecast_days", 3)
        }.body()
    }
}