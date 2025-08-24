package com.mrenann.dataagrin.core.data.local.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mrenann.dataagrin.core.data.local.dao.WeatherDao
import com.mrenann.dataagrin.core.data.local.entity.WeatherEntity

@Database(
    entities = [WeatherEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}