package com.androidgeek.weather.feature.weather.presentation

import com.androidgeek.weather.feature.weather.data.DataFixtures
import com.androidgeek.weather.feature.weather.data.mappers.toWeatherInfo
import com.androidgeek.weather.feature.weather.domain.location.model.Location
import com.androidgeek.weather.feature.weather.domain.location.repository.LocationRepository
import com.androidgeek.weather.feature.weather.domain.util.Resource
import com.androidgeek.weather.feature.weather.domain.weather.repository.WeatherRepository
import com.androidgeek.weather.util.CoroutinesExtension
import com.google.common.truth.Truth.assertThat
import io.mockk.Awaits
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

internal class WeatherViewModelTest {

    @JvmField
    @RegisterExtension
    val coroutinesExtension = CoroutinesExtension()

    private val weatherRepo: WeatherRepository = mockk()
    private val locationRepo: LocationRepository = mockk()

    private val sut = WeatherViewModel(weatherRepo, locationRepo)

    @Test
    fun initialization_emitsInitState() {
        assertThat(sut.state).isEqualTo(
            WeatherState(
                weatherInfo = null,
                isLoading = false,
                error = null
            )
        )
    }

    @Test
    fun loadWeatherInfo_awaitingLocation_emitsLoadingState() {
        coEvery { locationRepo.getCurrentLocation() } just Awaits

        sut.loadWeatherInfo()

        assertThat(sut.state).isEqualTo(
            WeatherState(
                weatherInfo = null,
                isLoading = true,
                error = null
            )
        )
    }

    @Test
    fun loadWeatherInfo_awaitingWeatherData_emitsLoadingState() {
        coEvery { locationRepo.getCurrentLocation() } returns Location(1.0, 2.0)
        coEvery { weatherRepo.getWeatherData(any(), any()) } just Awaits

        sut.loadWeatherInfo()

        assertThat(sut.state).isEqualTo(
            WeatherState(
                weatherInfo = null,
                isLoading = true,
                error = null
            )
        )
    }

    @Test
    fun loadWeatherInfo_validLocationAndWeatherData_emitsSuccessState() {
        coEvery { locationRepo.getCurrentLocation() } returns Location(1.0, 2.0)
        val weatherInfo = DataFixtures.getWeatherForecastDto().toWeatherInfo()
        coEvery { weatherRepo.getWeatherData(any(), any()) } returns Resource.Success(weatherInfo)

        sut.loadWeatherInfo()

        assertThat(sut.state).isEqualTo(
            WeatherState(
                weatherInfo = weatherInfo,
                isLoading = false,
                error = null
            )
        )
    }

    @Test
    fun loadWeatherInfo_noWeatherData_emitsErrorState() {
        coEvery { locationRepo.getCurrentLocation() } returns Location(1.0, 2.0)
        coEvery { weatherRepo.getWeatherData(any(), any()) } returns Resource.Error("Backend error")

        sut.loadWeatherInfo()

        assertThat(sut.state).isEqualTo(
            WeatherState(
                weatherInfo = null,
                isLoading = false,
                error = "Backend error"
            )
        )
    }

    @Test
    fun loadWeatherInfo_noCurrentLocation_emitsErrorState() {
        coEvery { locationRepo.getCurrentLocation() } returns null

        sut.loadWeatherInfo()

        assertThat(sut.state).isEqualTo(
            WeatherState(
                weatherInfo = null,
                isLoading = false,
                error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
            )
        )
    }
}
