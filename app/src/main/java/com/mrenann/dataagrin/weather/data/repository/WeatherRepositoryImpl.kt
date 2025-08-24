package com.mrenann.dataagrin.weather.data.repository

import com.mrenann.dataagrin.core.domain.model.WeatherInfo
import com.mrenann.dataagrin.core.utils.Resource
import com.mrenann.dataagrin.weather.domain.repository.WeatherRepository
import com.mrenann.dataagrin.weather.domain.source.WeatherLocalDataSource
import com.mrenann.dataagrin.weather.domain.source.WeatherRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherRepositoryImpl(
    private val remote: WeatherRemoteDataSource,
    private val local: WeatherLocalDataSource
) : WeatherRepository {

    override suspend fun getWeather(forceRefresh: Boolean): Flow<Resource<WeatherInfo>> = flow {
        emit(Resource.Loading)

        try {
            val cached = local.getWeather()
            if (cached != null && !forceRefresh) {
                emit(Resource.Success(cached, fromCache = true))
            }

            val remoteWeather = remote.fetchWeather(-23.55052, -46.633308)
            local.saveWeather(remoteWeather)

            emit(Resource.Success(remoteWeather, fromCache = false))

        } catch (e: Exception) {
            val fallback = local.getWeather()
            if (fallback != null) {
                emit(Resource.Success(fallback, fromCache = true))
            } else {
                emit(Resource.Error("Erro ao carregar clima", e))
            }
        }
    }
}

