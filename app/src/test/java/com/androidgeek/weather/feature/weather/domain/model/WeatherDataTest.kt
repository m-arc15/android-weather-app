package com.androidgeek.weather.feature.weather.domain.model

import com.androidgeek.weather.feature.weather.domain.DomainFixtures
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherData
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class WeatherDataTest {

    private lateinit var cut: WeatherData

    @Test
    fun getWeatherData_returnsDefaultValues() {
        cut = DomainFixtures.getWeatherData()

        val default = WeatherData(
            time = DomainFixtures.NOW,
            temperatureCelsius = 0.0,
            pressure = 0.0,
            windSpeed = 0.0,
            humidity = 0.0,
            weatherType = WeatherType.DEFAULT
        )
        assertThat(cut).isEqualTo(default)
    }

    @Test
    fun getWeatherData() {
        val expected = WeatherData(
            time = LocalDateTime.of(2020, 11, 5, 11, 23, 32),
            temperatureCelsius = 19.0,
            pressure = 1027.3,
            windSpeed = 17.6,
            humidity = 70.9,
            weatherType = WeatherType.ModerateRainShowers
        )

        cut = DomainFixtures.getWeatherData(
            expected.time,
            expected.temperatureCelsius,
            expected.pressure,
            expected.windSpeed,
            expected.humidity,
            expected.weatherType
        )

        assertThat(cut).isEqualTo(expected)
    }

}
