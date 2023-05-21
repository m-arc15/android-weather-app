package com.androidgeek.weather.features.weather.domain

import com.androidgeek.weather.features.weather.domain.model.WeatherData
import com.androidgeek.weather.features.weather.domain.model.WeatherType
import java.time.LocalDateTime

object DomainFixtures {

    val NOW: LocalDateTime = LocalDateTime.of(2023, 5, 20, 15, 30, 0)

    internal fun getWeatherData(
            time: LocalDateTime = NOW,
            temperatureCelsius: Double = 0.0,
            pressure: Double = 0.0,
            windSpeed: Double = 0.0,
            humidity: Double = 0.0,
            weatherType: WeatherType = WeatherType.DEFAULT
    ) = WeatherData(time, temperatureCelsius, pressure, windSpeed, humidity, weatherType)

}
