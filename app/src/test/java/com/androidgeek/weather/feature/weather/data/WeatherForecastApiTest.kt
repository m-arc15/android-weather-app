package com.androidgeek.weather.feature.weather.data

import com.androidgeek.weather.feature.weather.data.DataFixtures.currentLocation
import com.androidgeek.weather.feature.weather.data.remote.OpenMeteoWeatherForecastApi
import com.androidgeek.weather.feature.weather.data.remote.model.WeatherForecastApiErrorDTO
import com.androidgeek.weather.feature.weather.data.remote.model.WeatherForecastDataDTO
import com.androidgeek.weather.feature.weather.data.remote.model.WeatherForecastResponse
import com.androidgeek.weather.util.enqueueResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_OK
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

internal class WeatherForecastApiTest {

    private val server = MockWebServer()

    private val clientTimeoutInMillis = 30L
    private val client = OkHttpClient.Builder()
        .connectTimeout(clientTimeoutInMillis, TimeUnit.MILLISECONDS)
        .readTimeout(clientTimeoutInMillis, TimeUnit.MILLISECONDS)
        .writeTimeout(clientTimeoutInMillis, TimeUnit.MILLISECONDS)
        .build()

    private lateinit var api: OpenMeteoWeatherForecastApi

    @Before
    fun setUp() {
        val contentType = "application/json; charset=utf-8".toMediaType()
        val customJson = Json(builderAction = { ignoreUnknownKeys = true })
        val retrofit =
            Retrofit.Builder().baseUrl(server.url("/"))
                .client(client)
                .addConverterFactory(customJson.asConverterFactory(contentType))
                .build()

        api = retrofit.create(OpenMeteoWeatherForecastApi::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun getWeatherForecast_http200_returnsWeatherForecastResponse_minified() {
        server.enqueueResponse("forecast-200-minified.json", HTTP_OK)

        val actualResult = runBlocking {
            api.getWeatherForecast(
                latitude = currentLocation.latitude, longitude = currentLocation.longitude
            )
        }

        val expectedResult = WeatherForecastResponse(
            weatherForecastData = WeatherForecastDataDTO(
                listOf("2023-05-01T00:00", "2023-05-01T01:00", "2023-05-01T02:00"),
                listOf(6.1, 5.6, 4.9),
                listOf(1, 0, 0),
                listOf(1022.2, 1021.7, 1021.6),
                listOf(5.9, 5.5, 5.8),
                listOf(75.0, 79.0, 84.0)
            )
        )
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun getWeatherForecast_http200_returnsWeatherForecastResponse() {
        server.enqueueResponse("forecast-200.json", HTTP_OK)

        val actualResult = runBlocking {
            api.getWeatherForecast(
                latitude = currentLocation.latitude, longitude = currentLocation.longitude
            )
        }

        val data = actualResult.weatherForecastData
        val numberOfHoursInOneWeek = 24 * 7
        assertEquals(numberOfHoursInOneWeek, data.times.size)
        assertEquals(numberOfHoursInOneWeek, data.temperatures.size)
        assertEquals(numberOfHoursInOneWeek, data.weatherCodes.size)
        assertEquals(numberOfHoursInOneWeek, data.pressures.size)
        assertEquals(numberOfHoursInOneWeek, data.windSpeeds.size)
        assertEquals(numberOfHoursInOneWeek, data.humidities.size)
    }

    @Test
    fun getWeatherForecast_http400_throwsHttpException() {
        server.enqueueResponse("forecast-400.json", HTTP_BAD_REQUEST)

        try {
            runBlocking {
                api.getWeatherForecast(
                    latitude = currentLocation.latitude, longitude = currentLocation.longitude
                )
            }
            fail("Should have thrown HttpException before.")
        } catch (ex: HttpException) {
            val errorBodyString = ex.response()?.errorBody()?.string()!!
            val apiError = Json.decodeFromString<WeatherForecastApiErrorDTO>(errorBodyString)
            assertTrue(apiError.error)
            assertEquals(
                "Cannot initialize WeatherVariable from invalid String value tempeture_2m for key hourly",
                apiError.reason
            )
        }
    }

    @Test
    fun getWeatherForecast_timeout_throwsSocketTimeoutException() {
        server.enqueueResponse("forecast-200-minified.json", HTTP_OK, clientTimeoutInMillis)

        try {
            runBlocking {
                api.getWeatherForecast(
                    latitude = currentLocation.latitude, longitude = currentLocation.longitude
                )
            }
            fail("Should have thrown SocketTimeoutException before.")
        } catch (ex: SocketTimeoutException) {
            assertEquals("Read timed out", ex.localizedMessage)
        }
    }

}
