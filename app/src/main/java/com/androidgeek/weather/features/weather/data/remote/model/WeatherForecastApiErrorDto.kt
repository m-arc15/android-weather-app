package com.androidgeek.weather.features.weather.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherForecastApiErrorDto(
    val error: Boolean,
    val reason: String
)
