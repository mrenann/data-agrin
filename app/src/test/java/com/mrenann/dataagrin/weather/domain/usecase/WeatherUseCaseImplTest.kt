package com.mrenann.dataagrin.weather.domain.usecase

import app.cash.turbine.test
import com.mrenann.dataagrin.core.domain.model.WeatherInfo
import com.mrenann.dataagrin.core.utils.Resource
import com.mrenann.dataagrin.weather.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherUseCaseImplTest {

    private lateinit var repository: WeatherRepository
    private lateinit var useCase: GetWeatherUseCase

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = GetWeatherUseCaseImpl(repository)
    }

    @Test
    fun `invoke delegates to repository with default forceRefresh false and passes Success`() =
        runTest {
            val weather = mockk<WeatherInfo>()
            coEvery { repository.getWeather(false) } returns flowOf(
                Resource.Success(
                    weather,
                    fromCache = true
                )
            )

            val emissions = useCase().toList(mutableListOf())

            assertEquals(1, emissions.size)
            val s = emissions.single() as Resource.Success<WeatherInfo>
            assertTrue(s.fromCache)
            assertEquals(weather, s.data)

            coVerify(exactly = 1) { repository.getWeather(false) }
            confirmVerified(repository)
        }

    @Test
    fun `invoke delegates to repository with forceRefresh true and passes Success`() = runTest {
        val weather = mockk<WeatherInfo>()
        coEvery { repository.getWeather(true) } returns flowOf(
            Resource.Success(
                weather,
                fromCache = false
            )
        )

        val emissions = useCase(forceRefresh = true).toList(mutableListOf())

        assertEquals(1, emissions.size)
        val s = emissions.single() as Resource.Success<WeatherInfo>
        assertFalse(s.fromCache)
        assertEquals(weather, s.data)

        coVerify(exactly = 1) { repository.getWeather(true) }
        confirmVerified(repository)
    }

    @Test
    fun `invoke propagates error from repository flow`() = runTest {
        coEvery { repository.getWeather(false) } returns flow { throw RuntimeException("boom") }

        useCase().test {
            val e = awaitError()
            assertEquals("boom", e.message)
        }

        coVerify(exactly = 1) { repository.getWeather(false) }
        confirmVerified(repository)
    }
}