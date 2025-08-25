package com.mrenann.dataagrin.core.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.mrenann.dataagrin.core.data.local.databases.WeatherDatabase
import com.mrenann.dataagrin.core.data.local.entity.WeatherEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class WeatherDaoTest {

    private lateinit var database: WeatherDatabase
    private lateinit var weatherDao: WeatherDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        ).allowMainThreadQueries().build()
        weatherDao = database.weatherDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertWeather_and_getWeather() = runTest {
        val weatherData = WeatherEntity(
            id = 1, temperature = 25.5, code = 800, humidity = 60,
            rain = 0.0,
            isDay = false,
            condition = "Sunny",
            city = "Test City",
            updatedAt = System.currentTimeMillis(),
            hourly = listOf()
        )
        weatherDao.insertWeather(weatherData)

        val retrievedWeather = weatherDao.getWeather()

        assertNotNull(retrievedWeather)
        assertEquals(weatherData.id, retrievedWeather?.id)
        assertEquals(weatherData.temperature, retrievedWeather?.temperature)
    }

    @Test
    fun clear_deletesAllWeatherData() = runTest {
        val weatherData = WeatherEntity(
            id = 1, temperature = 22.0, code = 803, humidity = 75,
            rain = 0.5,
            isDay = false,
            condition = "Cloudy",
            city = "Another City",
            updatedAt = System.currentTimeMillis() - 100000,
            hourly = listOf()
        )
        weatherDao.insertWeather(weatherData)

        weatherDao.clear()

        val retrievedWeather = weatherDao.getWeather()
        assertNull(retrievedWeather)
    }

    @Test
    fun getWeather_whenDbIsEmpty_returnsNull() = runTest {
        val retrievedWeather = weatherDao.getWeather()
        assertNull(retrievedWeather)
    }
}