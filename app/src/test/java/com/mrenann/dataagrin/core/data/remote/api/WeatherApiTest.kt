package com.mrenann.dataagrin.core.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherApiImplTest {

    private fun getJsonFromResource(fileName: String): String {
        val resource = this::class.java.classLoader?.getResource(fileName)
        checkNotNull(resource) { "Resource file not found: $fileName" }
        return resource.readText()
    }

    @Test
    fun `getWeatherData should return parsed WeatherResponse on success`() = runTest {
        val mockResponseJson = getJsonFromResource("success_weather_response.json")
        val mockEngine = MockEngine { request ->
            assertEquals("api.open-meteo.com", request.url.host)
            assertEquals("/v1/forecast", request.url.encodedPath)
            assertEquals("America/Sao_Paulo", request.url.parameters["timezone"])

            respond(
                content = mockResponseJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }

        val weatherApi = WeatherApiImpl(client)

        val result = weatherApi.getWeatherData(latitude = -23.55, longitude = -46.63)

        assertEquals(18.5, result.current?.temperature2m ?: 0.0, 0.0)
        assertEquals(75, result.current?.relativeHumidity2m ?: 0)
        assertEquals(2, result.hourly?.time?.size)
    }

}