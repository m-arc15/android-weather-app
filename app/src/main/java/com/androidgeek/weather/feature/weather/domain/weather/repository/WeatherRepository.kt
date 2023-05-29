package com.androidgeek.weather.feature.weather.domain.weather.repository

import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherInfo
import com.androidgeek.weather.feature.weather.domain.util.Resource

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}
