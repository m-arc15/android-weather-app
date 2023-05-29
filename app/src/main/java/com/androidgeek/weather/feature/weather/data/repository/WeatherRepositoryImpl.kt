package com.androidgeek.weather.feature.weather.data.repository

import com.androidgeek.weather.feature.weather.data.mappers.toWeatherInfo
import com.androidgeek.weather.feature.weather.data.remote.api.OpenMeteoWeatherForecastApi
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherInfo
import com.androidgeek.weather.feature.weather.domain.weather.repository.WeatherRepository
import com.androidgeek.weather.feature.weather.domain.util.Resource
import timber.log.Timber
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
        private val api: OpenMeteoWeatherForecastApi
) : WeatherRepository {
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherForecast(
                    latitude = lat,
                    longitude = long
                ).toWeatherInfo()
            )
        } catch (ex: Exception) {
            Timber.e(ex)
            Resource.Error(ex.message ?: "An unknown error occurred.")
        }
    }
}
