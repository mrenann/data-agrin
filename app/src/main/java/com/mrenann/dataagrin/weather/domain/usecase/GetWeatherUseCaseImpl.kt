package com.mrenann.dataagrin.weather.domain.usecase

import com.mrenann.dataagrin.core.domain.model.WeatherInfo
import com.mrenann.dataagrin.core.utils.Resource
import com.mrenann.dataagrin.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class GetWeatherUseCaseImpl(
    private val repository: WeatherRepository
) : GetWeatherUseCase {

    override suspend operator fun invoke(forceRefresh: Boolean): Flow<Resource<WeatherInfo>> {
        return repository.getWeather(forceRefresh)
    }
}
