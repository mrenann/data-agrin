// Kotlin
package com.mrenann.dataagrin.weather.data.source

import com.mrenann.dataagrin.core.data.local.dao.WeatherDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherLocalDataSourceImplTest {

    private lateinit var dao: WeatherDao
    private lateinit var dataSource: WeatherLocalDataSourceImpl

    @Before
    fun setUp() {
        dao = mockk(relaxed = true)
        dataSource = WeatherLocalDataSourceImpl(dao)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getWeather returns null when there is no cache`() = runTest {
        coEvery { dao.getWeather() } returns null

        val result = dataSource.getWeather()

        assertEquals(null, result)
        coVerify(exactly = 1) { dao.getWeather() }
        confirmVerified(dao)
    }

    @Test
    fun `getWeather propagates error when DAO fails`() = runTest {
        val ex = RuntimeException("boom")
        coEvery { dao.getWeather() } throws ex

        val captured = runCatching { dataSource.getWeather() }.exceptionOrNull()

        assertEquals(ex, captured)
        coVerify(exactly = 1) { dao.getWeather() }
        confirmVerified(dao)
    }

}