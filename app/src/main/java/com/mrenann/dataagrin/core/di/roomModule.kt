package com.mrenann.dataagrin.core.di

import androidx.room.Room
import com.mrenann.dataagrin.core.data.local.dao.WeatherDao
import com.mrenann.dataagrin.core.data.local.databases.WeatherDatabase
import com.mrenann.dataagrin.core.utils.Constants.DatabaseName
import org.koin.dsl.module

val roomModule = module {
    single {
        Room.databaseBuilder(get(), WeatherDatabase::class.java, DatabaseName)
            .build()
    }

    single<WeatherDao> {
        val database = get<WeatherDatabase>()
        database.weatherDao()
    }

}