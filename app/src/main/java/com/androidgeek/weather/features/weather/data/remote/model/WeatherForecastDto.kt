package com.androidgeek.weather.features.weather.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherForecastDto(
    @SerialName("hourly")
    val weatherForecastData: WeatherForecastDataDto
)
