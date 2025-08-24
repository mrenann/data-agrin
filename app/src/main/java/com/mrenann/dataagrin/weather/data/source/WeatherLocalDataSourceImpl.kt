package com.mrenann.dataagrin.weather.data.source

import com.mrenann.dataagrin.core.data.local.dao.WeatherDao
import com.mrenann.dataagrin.core.data.local.mappers.toDomain
import com.mrenann.dataagrin.core.data.local.mappers.toEntity
import com.mrenann.dataagrin.core.domain.model.WeatherInfo
import com.mrenann.dataagrin.weather.domain.source.WeatherLocalDataSource

class WeatherLocalDataSourceImpl(
    private val dao: WeatherDao
) : WeatherLocalDataSource {

    override suspend fun getWeather(): WeatherInfo? {
        return dao.getWeather()?.toDomain()
    }

    override suspend fun saveWeather(weather: WeatherInfo) {
        dao.insertWeather(
            weather.toEntity(
                city = "",
                condition = "",
                updatedAt = System.currentTimeMillis()
            )
        )
    }
}