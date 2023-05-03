package com.androidgeek.weather.feature.weather.data

import android.location.Location

object DataFixtures {
    internal val currentLocation: Location by lazy {
        // London
        Location("DataFixtures").apply {
            latitude = 51.51
            longitude = -0.13
        }
    }
}
