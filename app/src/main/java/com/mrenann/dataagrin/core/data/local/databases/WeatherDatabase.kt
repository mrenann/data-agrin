package com.mrenann.dataagrin.core.data.local.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mrenann.dataagrin.core.data.local.dao.ActivityDao
import com.mrenann.dataagrin.core.data.local.dao.WeatherDao
import com.mrenann.dataagrin.core.data.local.entity.ActivityEntity
import com.mrenann.dataagrin.core.data.local.entity.WeatherEntity

@Database(
    entities = [WeatherEntity::class, ActivityEntity::class],
    version = 2,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun activityDao(): ActivityDao
}
