// Kotlin
package com.mrenann.dataagrin.weather.data.source

import com.mrenann.dataagrin.core.data.remote.api.WeatherApi
import com.mrenann.dataagrin.weather.domain.source.WeatherRemoteDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRemoteDataSourceImplTest {

    private lateinit var api: WeatherApi
    private lateinit var dataSource: WeatherRemoteDataSource

    @Before
    fun setUp() {
        api = mockk(relaxed = true)
        dataSource = WeatherRemoteDataSourceImpl(api)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }


    @Test
    fun `fetchWeather propagates exception when api fails`() = runTest {
        val lat = -1.0
        val lon = 1.0
        coEvery { api.getWeatherData(lat, lon) } throws RuntimeException("boom")

        try {
            dataSource.fetchWeather(lat, lon)
            throw AssertionError("Expected exception was not thrown")
        } catch (e: RuntimeException) {
            assertEquals("boom", e.message)
        }

        coVerify(exactly = 1) { api.getWeatherData(lat, lon) }
    }
}