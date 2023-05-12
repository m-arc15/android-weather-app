package com.androidgeek.weather.features.weather.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherForecastResponse(
    @SerialName("hourly")
    val weatherForecastData: WeatherForecastDataDTO
)
