package com.mrenann.dataagrin.weather.domain.usecase

import com.mrenann.dataagrin.core.domain.model.WeatherInfo
import com.mrenann.dataagrin.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetWeatherUseCase {
    suspend operator fun invoke(forceRefresh: Boolean = false): Flow<Resource<WeatherInfo>>
}
