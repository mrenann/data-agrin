package com.mrenann.dataagrin.weather.data.source

import com.mrenann.dataagrin.core.data.remote.api.WeatherApi
import com.mrenann.dataagrin.core.data.remote.mappers.toDomain
import com.mrenann.dataagrin.core.domain.model.WeatherInfo
import com.mrenann.dataagrin.weather.domain.source.WeatherRemoteDataSource

class WeatherRemoteDataSourceImpl(
    private val api: WeatherApi
) : WeatherRemoteDataSource {

    override suspend fun fetchWeather(latitude: Double, longitude: Double): WeatherInfo {
        val response = api.getWeatherData(latitude, longitude)
        return response.toDomain()
    }
}