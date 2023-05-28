package com.androidgeek.weather.features.weather.domain.repository

import com.androidgeek.weather.features.weather.domain.model.WeatherInfo
import com.androidgeek.weather.features.weather.domain.util.Resource

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}
