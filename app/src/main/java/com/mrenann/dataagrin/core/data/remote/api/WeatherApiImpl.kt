package com.mrenann.dataagrin.core.data.remote.api

import com.mrenann.dataagrin.BuildConfig
import com.mrenann.dataagrin.core.data.remote.response.WeatherResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class WeatherApiImpl(private val client: HttpClient) : WeatherApi {

    companion object {
        private const val HOURLY_PARAMS =
            "temperature_2m,relative_humidity_2m,is_day,rain,weather_code"
        private const val CURRENT_PARAMS =
            "temperature_2m,relative_humidity_2m,rain,is_day,weather_code"
        private const val TIMEZONE = "America/Sao_Paulo"
        private const val FORECAST_DAYS = 1
    }

    override suspend fun getWeatherData(
        latitude: Double,
        longitude: Double,
    ): WeatherResponse {
        return client.get(BuildConfig.BASE_URL) {
            parameter("latitude", latitude)
            parameter("longitude", longitude)
            parameter("hourly", HOURLY_PARAMS)
            parameter("current", CURRENT_PARAMS)
            parameter("timezone", TIMEZONE)
            parameter("forecast_days", FORECAST_DAYS)
        }.body()
    }
}
