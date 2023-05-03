package com.androidgeek.weather.feature.weather.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherForecastApiErrorDTO(
    val error: Boolean,
    val reason: String
)
