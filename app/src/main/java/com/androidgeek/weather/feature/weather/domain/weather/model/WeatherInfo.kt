package com.androidgeek.weather.feature.weather.domain.weather.model

import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherData

/**
 * Contains information about a weather per day.
 * - weatherDataPerDay - mapping of day index to hourly list of weather data
 *     Example:
 *         - when key is 0, return weather data about today
 *         - when key is 1, return weather data about tomorrow
 * - currentWeatherData - weather data for current hour of today
 *     Example: when we request data at 10am, it will contains the weather data for 10am
 */
data class WeatherInfo(
        val weatherDataPerDay: Map<Int, List<WeatherData>>,
        val currentWeatherData: WeatherData?
)
