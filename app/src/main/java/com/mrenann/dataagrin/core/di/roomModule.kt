package com.mrenann.dataagrin.core.di

import androidx.room.Room
import com.mrenann.dataagrin.core.data.local.dao.ActivityDao
import com.mrenann.dataagrin.core.data.local.dao.WeatherDao
import com.mrenann.dataagrin.core.data.local.databases.WeatherDatabase
import com.mrenann.dataagrin.core.utils.Constants.DATABASE_NAME
import org.koin.dsl.module

val roomModule = module {
    single {
        Room.databaseBuilder(get(), WeatherDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    }

    single<WeatherDao> {
        val database = get<WeatherDatabase>()
        database.weatherDao()
    }

    single<ActivityDao> {
        val database = get<WeatherDatabase>()
        database.activityDao()
    }

}