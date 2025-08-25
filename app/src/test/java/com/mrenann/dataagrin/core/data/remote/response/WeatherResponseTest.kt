package com.mrenann.dataagrin.core.data.remote.response

import com.mrenann.dataagrin.core.data.remote.mappers.toDomain
import com.mrenann.dataagrin.core.data.remote.model.weather.Current
import com.mrenann.dataagrin.core.data.remote.model.weather.Hourly
import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherResponseTest {

    @Test
    fun `toDomain should correctly map valid response`() {
        val mockResponse = WeatherResponse(
            current = Current(
                temperature2m = 20.5,
                relativeHumidity2m = 65,
                rain = 0.0,
                isDay = 1,
                weatherCode = 1
            ),
            hourly = Hourly(
                time = listOf("10:00", "11:00"),
                temperature2m = listOf(20.0, 21.0),
                relativeHumidity2m = listOf(66, 64),
                rain = listOf(0.0, 0.0),
                isDay = listOf(1, 1),
                weatherCode = listOf(2, 3)
            )
        )

        val domainInfo = mockResponse.toDomain()

        assertEquals(20.5, domainInfo.current.temperature, 0.0)
        assertEquals(65, domainInfo.current.humidity)
        assertEquals(true, domainInfo.current.isDay)
        assertEquals(1, domainInfo.current.code)

        assertEquals(2, domainInfo.hourly.size)
        assertEquals(21.0, domainInfo.hourly[1].temperature, 0.0)
        assertEquals(64, domainInfo.hourly[1].humidity)
    }

    @Test
    fun `toDomain should handle null current data gracefully`() {
        val mockResponse = WeatherResponse(
            current = null,
            hourly = Hourly(
                time = listOf("10:00"),
                temperature2m = listOf(20.0),
                relativeHumidity2m = listOf(66),
                rain = listOf(0.0),
                isDay = listOf(1),
                weatherCode = listOf(2)
            )
        )

        val domainInfo = mockResponse.toDomain()

        assertEquals(0.0, domainInfo.current.temperature, 0.0)
        assertEquals(0, domainInfo.current.humidity)
    }

    @Test
    fun `toDomain should handle null hourly data gracefully`() {
        val mockResponse = WeatherResponse(
            current = Current(
                temperature2m = 20.5,
                relativeHumidity2m = 65,
                rain = 0.0,
                isDay = 1,
                weatherCode = 1
            ),
            hourly = null
        )

        val domainInfo = mockResponse.toDomain()

        assertEquals(0, domainInfo.hourly.size)
    }
}