package com.androidgeek.weather.feature.weather.domain.location.repository

import com.androidgeek.weather.feature.weather.domain.location.model.Location

interface LocationRepository {
    suspend fun getCurrentLocation(): Location?
}
