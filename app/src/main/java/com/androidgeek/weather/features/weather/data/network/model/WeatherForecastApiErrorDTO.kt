package com.androidgeek.weather.features.weather.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherForecastApiErrorDTO(
    val error: Boolean,
    val reason: String
)
