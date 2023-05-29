package com.androidgeek.weather.feature.weather.data.mappers

import com.androidgeek.weather.feature.weather.data.remote.model.WeatherForecastDataDto
import com.androidgeek.weather.feature.weather.data.remote.model.WeatherForecastDto
import com.androidgeek.weather.feature.weather.domain.model.WeatherData
import com.androidgeek.weather.feature.weather.domain.model.WeatherInfo
import com.androidgeek.weather.feature.weather.domain.model.WeatherType
import java.time.LocalDateTime

private const val HOURS_PER_DAY = 24

private data class IndexedWeatherData(
        val index: Int,
        val data: WeatherData
)

fun WeatherForecastDataDto.toWeatherDataPerDay(): Map<Int, List<WeatherData>> {
    return times.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val pressure = pressures[index]
        val windSpeed = windSpeeds[index]
        val humidity = humidities[index]
        val weatherCode = weatherCodes[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        it.index / HOURS_PER_DAY
    }.mapValues { indexedWeatherData ->
        indexedWeatherData.value.map { it.data }
    }
}

fun WeatherForecastDto.toWeatherInfo(
        now: LocalDateTime = LocalDateTime.now()
): WeatherInfo {
    val weatherDataPerDay = weatherForecastData.toWeatherDataPerDay()
    val currentHour = if (now.minute < 30) now.hour else now.hour + 1
    val currentWeatherData = if (now.hour < 23) weatherDataPerDay[0]?.find { it.time.hour == currentHour } else weatherDataPerDay[1]!![0]
    return WeatherInfo(
        weatherDataPerDay = weatherDataPerDay,
        currentWeatherData = currentWeatherData
    )
}
