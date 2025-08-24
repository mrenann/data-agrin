package com.mrenann.dataagrin.core.data.local.mappers

import com.mrenann.dataagrin.core.data.local.entity.HourlyForecastEntity
import com.mrenann.dataagrin.core.data.local.entity.WeatherEntity
import com.mrenann.dataagrin.core.domain.model.HourlyForecast
import com.mrenann.dataagrin.core.domain.model.Weather
import com.mrenann.dataagrin.core.domain.model.WeatherInfo

fun WeatherEntity.toDomain(): WeatherInfo = WeatherInfo(
    current = Weather(
        temperature = temperature,
        humidity = humidity,
        rain = rain,
        isDay = isDay,
    ),
    hourly = hourly.map {
        HourlyForecast(
            time = it.time,
            temperature = it.temperature,
            humidity = it.humidity,
            rain = it.rain,
            isDay = it.isDay
        )
    }
)


fun WeatherInfo.toEntity(city: String, condition: String, updatedAt: Long): WeatherEntity =
    WeatherEntity(
        temperature = current.temperature,
        humidity = current.humidity,
        rain = current.rain,
        isDay = current.isDay,
        condition = condition,
        city = city,
        updatedAt = updatedAt,
        hourly = hourly.map {
            HourlyForecastEntity(
                time = it.time,
                temperature = it.temperature,
                humidity = it.humidity,
                rain = it.rain,
                isDay = it.isDay
            )
        }
    )
