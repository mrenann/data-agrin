package com.mrenann.dataagrin.weather.di

import com.mrenann.dataagrin.weather.data.repository.WeatherRepositoryImpl
import com.mrenann.dataagrin.weather.data.source.WeatherLocalDataSourceImpl
import com.mrenann.dataagrin.weather.data.source.WeatherRemoteDataSourceImpl
import com.mrenann.dataagrin.weather.domain.repository.WeatherRepository
import com.mrenann.dataagrin.weather.domain.source.WeatherLocalDataSource
import com.mrenann.dataagrin.weather.domain.source.WeatherRemoteDataSource
import com.mrenann.dataagrin.weather.domain.usecase.GetWeatherUseCase
import com.mrenann.dataagrin.weather.domain.usecase.GetWeatherUseCaseImpl
import org.koin.dsl.module

val weatherModule =
    module {
        single<WeatherLocalDataSource> {
            WeatherLocalDataSourceImpl(
                dao = get(),
            )
        }
        single<WeatherRemoteDataSource> {
            WeatherRemoteDataSourceImpl(
                api = get()
            )
        }
        single<WeatherRepository> {
            WeatherRepositoryImpl(
                remote = get(),
                local = get()
            )
        }
        single<GetWeatherUseCase> {
            GetWeatherUseCaseImpl(
                repository = get(),
            )
        }


    }
