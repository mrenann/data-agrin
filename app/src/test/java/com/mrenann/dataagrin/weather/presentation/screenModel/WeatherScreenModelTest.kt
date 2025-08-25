package com.mrenann.dataagrin.weather.presentation.screenModel

import app.cash.turbine.test
import com.mrenann.dataagrin.core.domain.model.Weather
import com.mrenann.dataagrin.core.domain.model.WeatherInfo
import com.mrenann.dataagrin.core.utils.Resource
import com.mrenann.dataagrin.weather.domain.usecase.GetWeatherUseCase
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

@ExperimentalCoroutinesApi
class WeatherScreenModelTest {
    private val getWeatherUseCase = mockk<GetWeatherUseCase>()
    private val dispatcher = StandardTestDispatcher()
    private lateinit var screenModel: WeatherScreenModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        clearAllMocks()
        coEvery { getWeatherUseCase(any()) } returns flowOf(Resource.Loading)
        screenModel = WeatherScreenModel(getWeatherUseCase = getWeatherUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be Loading`() = runTest {
        assertTrue(screenModel.state.value is WeatherScreenModel.State.Loading)
    }

    @Test
    fun `loadWeather should emit Success when use case is successful`() = runTest {
        val mockWeather = WeatherInfo(
            current = Weather(
                temperature = 25.0,
                humidity = 60,
                isDay = true,
                rain = 0.0,
                code = 1,
            ),
            hourly = emptyList()
        )

        coEvery { getWeatherUseCase(any()) } returns flowOf(
            Resource.Success(mockWeather, fromCache = false)
        )

        screenModel.loadWeather()

        screenModel.state.test(timeout = 3.seconds) {
            val loadingState = awaitItem()
            assertTrue(loadingState is WeatherScreenModel.State.Loading)

            val successState = awaitItem()
            assertTrue(successState is WeatherScreenModel.State.Success)

            val result = successState as WeatherScreenModel.State.Success
            assertEquals(mockWeather, result.weather)
            assertEquals(false, result.fromCache)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadWeather should emit Error when use case fails`() = runTest {
        val errorMessage = "Network request failed"

        coEvery { getWeatherUseCase(any()) } returns flowOf(
            Resource.Error(errorMessage)
        )

        screenModel.loadWeather()

        screenModel.state.test(timeout = 3.seconds) {
            val loadingState = awaitItem()
            assertTrue(loadingState is WeatherScreenModel.State.Loading)

            val errorState = awaitItem()
            assertTrue(errorState is WeatherScreenModel.State.Error)
            assertEquals(errorMessage, (errorState as WeatherScreenModel.State.Error).message)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadWeather should emit Success with fromCache true when data is from cache`() = runTest {
        val mockWeather = WeatherInfo(
            current = Weather(
                temperature = 25.0,
                humidity = 60,
                isDay = true,
                rain = 0.0,
                code = 1,
            ),
            hourly = emptyList()
        )

        coEvery { getWeatherUseCase(any()) } returns flowOf(
            Resource.Success(mockWeather, fromCache = true)
        )

        screenModel.loadWeather()

        screenModel.state.test(timeout = 3.seconds) {
            val loadingState = awaitItem()
            assertTrue(loadingState is WeatherScreenModel.State.Loading)

            val successState = awaitItem()
            assertTrue(successState is WeatherScreenModel.State.Success)

            val result = successState as WeatherScreenModel.State.Success
            assertEquals(mockWeather, result.weather)
            assertEquals(true, result.fromCache)

            cancelAndIgnoreRemainingEvents()
        }
    }

}