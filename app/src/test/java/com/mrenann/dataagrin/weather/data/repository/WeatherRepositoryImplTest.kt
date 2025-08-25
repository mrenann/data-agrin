package com.mrenann.dataagrin.weather.data.repository

import app.cash.turbine.test
import com.mrenann.dataagrin.core.domain.model.WeatherInfo
import com.mrenann.dataagrin.core.utils.Resource
import com.mrenann.dataagrin.weather.domain.repository.WeatherRepository
import com.mrenann.dataagrin.weather.domain.source.WeatherLocalDataSource
import com.mrenann.dataagrin.weather.domain.source.WeatherRemoteDataSource
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryImplTest {

    private lateinit var remote: WeatherRemoteDataSource
    private lateinit var local: WeatherLocalDataSource
    private lateinit var repository: WeatherRepository

    @Before
    fun setUp() {
        remote = mockk(relaxed = true)
        local = mockk(relaxed = true)
        repository = WeatherRepositoryImpl(remote, local)
    }

    @Test
    fun `getWeather emits Loading, then Success from cache, then remote Success when cache exists and forceRefresh is false`() =
        runTest {
            val cached = mockk<WeatherInfo>()
            val remoteWeather = mockk<WeatherInfo>()
            coEvery { local.getWeather() } returns cached
            coEvery {
                remote.fetchWeather(
                    latitude = -23.55052,
                    longitude = -46.633308
                )
            } returns remoteWeather
            coEvery { local.saveWeather(remoteWeather) } just Runs

            repository.getWeather(forceRefresh = false).test {
                assertTrue(awaitItem() is Resource.Loading)

                val s1 = awaitItem() as Resource.Success<WeatherInfo>
                assertTrue(s1.fromCache)
                assertEquals(cached, s1.data)

                val s2 = awaitItem() as Resource.Success<WeatherInfo>
                assertFalse(s2.fromCache)
                assertEquals(remoteWeather, s2.data)

                awaitComplete()
            }

            coVerify(exactly = 1) { local.getWeather() }
            coVerify(exactly = 1) {
                remote.fetchWeather(
                    latitude = -23.55052,
                    longitude = -46.633308
                )
            }
            coVerify(exactly = 1) { local.saveWeather(remoteWeather) }
            confirmVerified(remote, local)
        }

    @Test
    fun `getWeather emits Loading and remote Success when there is no cache`() = runTest {
        val remoteWeather = mockk<WeatherInfo>()
        coEvery { local.getWeather() } returns null
        coEvery {
            remote.fetchWeather(
                latitude = -23.55052,
                longitude = -46.633308
            )
        } returns remoteWeather
        coEvery { local.saveWeather(remoteWeather) } just Runs

        repository.getWeather().test {
            assertTrue(awaitItem() is Resource.Loading)

            val s = awaitItem() as Resource.Success<WeatherInfo>
            assertFalse(s.fromCache)
            assertEquals(remoteWeather, s.data)

            awaitComplete()
        }

        coVerify(exactly = 1) { local.getWeather() }
        coVerify(exactly = 1) { remote.fetchWeather(latitude = -23.55052, longitude = -46.633308) }
        coVerify(exactly = 1) { local.saveWeather(remoteWeather) }
        confirmVerified(remote, local)
    }

    @Test
    fun `getWeather ignores cache when forceRefresh is true and emits only remote Success`() =
        runTest {
            val cached = mockk<WeatherInfo>()
            val remoteWeather = mockk<WeatherInfo>()
            coEvery { local.getWeather() } returns cached
            coEvery {
                remote.fetchWeather(
                    latitude = -23.55052,
                    longitude = -46.633308
                )
            } returns remoteWeather
            coEvery { local.saveWeather(remoteWeather) } just Runs

            repository.getWeather(forceRefresh = true).test {
                assertTrue(awaitItem() is Resource.Loading)

                val s = awaitItem() as Resource.Success<WeatherInfo>
                assertFalse(s.fromCache)
                assertEquals(remoteWeather, s.data)

                awaitComplete()
            }

            coVerify(exactly = 1) { local.getWeather() }
            coVerify(exactly = 1) {
                remote.fetchWeather(
                    latitude = -23.55052,
                    longitude = -46.633308
                )
            }
            coVerify(exactly = 1) { local.saveWeather(remoteWeather) }
            confirmVerified(remote, local)
        }


    @Test
    fun `getWeather emits Error when remote fails and there is no cache`() = runTest {
        coEvery { local.getWeather() } returns null
        coEvery { remote.fetchWeather(any(), any()) } throws RuntimeException("fail")

        repository.getWeather().test {
            assertTrue(awaitItem() is Resource.Loading)

            val e = awaitItem() as Resource.Error
            assertEquals("Erro ao carregar clima", e.message)

            awaitComplete()
        }

        coVerify(exactly = 2) { local.getWeather() }
        coVerify(exactly = 1) { remote.fetchWeather(latitude = -23.55052, longitude = -46.633308) }
        coVerify(exactly = 0) { local.saveWeather(any()) }
        confirmVerified(remote, local)
    }
}