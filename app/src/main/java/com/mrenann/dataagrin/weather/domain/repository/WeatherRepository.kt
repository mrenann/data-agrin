package com.mrenann.dataagrin.weather.domain.repository

import com.mrenann.dataagrin.core.domain.model.WeatherInfo
import com.mrenann.dataagrin.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeather(forceRefresh: Boolean = false): Flow<Resource<WeatherInfo>>
}
