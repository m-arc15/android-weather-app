package com.androidgeek.weather.feature.weather.data.repository

import com.androidgeek.weather.feature.weather.data.DataFixtures
import com.androidgeek.weather.feature.weather.data.remote.api.OpenMeteoWeatherForecastApi
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherInfo
import com.androidgeek.weather.feature.weather.domain.util.Resource
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class WeatherRepositoryImplTest {

    private val api: OpenMeteoWeatherForecastApi = mockk()

    private val sut: WeatherRepositoryImpl = WeatherRepositoryImpl(api)

    @Test
    fun getWeatherData_returnsSuccess() = runTest {
        coEvery { api.getWeatherForecast(any(), any()) } returns DataFixtures.getWeatherForecastDto(
            DataFixtures.generateWeatherForecastDataFor(7)
        )

        val actual = sut.getWeatherData(1.0, 1.0)

        assertThat(actual).isInstanceOf(Resource.Success::class.java)
        assertThat(actual.data).isInstanceOf(WeatherInfo::class.java)
        assertThat(actual.data!!.currentWeatherData).isNotNull()
        assertThat(actual.data!!.weatherDataPerDay.size).isEqualTo(7)
        assertThat(actual.message).isNull()
    }

    @Test
    fun getWeatherData_returnsSpecifiedError() = runTest {
        coEvery { api.getWeatherForecast(any(), any()) } throws RuntimeException("Server error")

        val actual = sut.getWeatherData(1.0, 1.0)

        assertThat(actual).isInstanceOf(Resource.Error::class.java)
        assertThat(actual.data).isNull()
        assertThat(actual.message).isEqualTo("Server error")
    }

    @Test
    fun getWeatherData_returnsUnknownError() = runTest {
        coEvery { api.getWeatherForecast(any(), any()) } throws RuntimeException()

        val actual = sut.getWeatherData(1.0, 1.0)

        assertThat(actual).isInstanceOf(Resource.Error::class.java)
        assertThat(actual.data).isNull()
        assertThat(actual.message).isEqualTo("An unknown error occurred.")
    }

}
