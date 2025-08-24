package com.mrenann.dataagrin.core.data.remote.mappers

import com.mrenann.dataagrin.core.data.remote.response.WeatherResponse
import com.mrenann.dataagrin.core.domain.model.HourlyForecast
import com.mrenann.dataagrin.core.domain.model.Weather
import com.mrenann.dataagrin.core.domain.model.WeatherInfo

fun WeatherResponse.toDomain(): WeatherInfo {
    val currentWeather = Weather(
        temperature = current?.temperature2m ?: 0.0,
        humidity = current?.relativeHumidity2m ?: 0,
        rain = current?.rain ?: 0.0,
        isDay = current?.isDay == 1
    )

    val hourlyForecast = if (hourly?.time != null &&
        hourly.temperature2m != null &&
        hourly.relativeHumidity2m != null &&
        hourly.rain != null &&
        hourly.isDay != null
    ) {
        hourly.time.mapIndexed { index, time ->
            HourlyForecast(
                time = time.orEmpty(),
                temperature = hourly.temperature2m.getOrNull(index) ?: 0.0,
                humidity = hourly.relativeHumidity2m.getOrNull(index) ?: 0,
                rain = hourly.rain.getOrNull(index) ?: 0.0,
                isDay = hourly.isDay.getOrNull(index) == 1
            )
        }
    } else {
        emptyList()
    }

    return WeatherInfo(
        current = currentWeather,
        hourly = hourlyForecast
    )
}
