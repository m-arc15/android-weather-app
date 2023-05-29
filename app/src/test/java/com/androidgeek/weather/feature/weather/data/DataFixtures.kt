package com.androidgeek.weather.feature.weather.data

import com.androidgeek.weather.feature.weather.data.remote.model.WeatherForecastDataDto
import com.androidgeek.weather.feature.weather.data.remote.model.WeatherForecastDto
import com.androidgeek.weather.feature.weather.domain.DomainFixtures.NOW

object DataFixtures {

    internal fun getWeatherForecastDataDto(
            times: List<String> = emptyList(),
            temperatures: List<Double> = emptyList(),
            weatherCodes: List<Int> = emptyList(),
            pressures: List<Double> = emptyList(),
            windSpeeds: List<Double> = emptyList(),
            humidities: List<Double> = emptyList()
    ) = WeatherForecastDataDto(times, temperatures, weatherCodes, pressures, windSpeeds, humidities)

    internal fun getWeatherForecastDto(
            weatherForecastData: WeatherForecastDataDto = getWeatherForecastDataDto()
    ) = WeatherForecastDto(weatherForecastData)

    internal fun generateWeatherForecastDataFor(numberOfDays: Int): WeatherForecastDataDto {
        val size = numberOfDays * 24

        val today = NOW.withHour(0).withMinute(0)

        return WeatherForecastDataDto(
            times = (0 until size).map { today.plusHours(it.toLong()).toString()},
            temperatures = MutableList(size) { 10.0 },
            weatherCodes = MutableList(size) { 0 },
            pressures = MutableList(size) { 1010.0 },
            windSpeeds = MutableList(size) { 4.0 },
            humidities = MutableList(size) { 80.0 }
        )
    }
}
