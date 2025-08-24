package com.mrenann.dataagrin.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mrenann.dataagrin.core.data.local.entity.WeatherEntity

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather LIMIT 1")
    suspend fun getWeather(): WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Query("DELETE FROM weather")
    suspend fun clear()
}