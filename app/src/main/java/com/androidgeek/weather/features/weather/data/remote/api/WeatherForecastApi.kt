package com.androidgeek.weather.features.weather.data.remote.api

import com.androidgeek.weather.features.weather.data.remote.model.WeatherForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoWeatherForecastApi {

    @GET(FORECAST_ENDPOINT_PATH)
    suspend fun getWeatherForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: List<String> = FORECAST_HOURLY_PARAMS
    ): WeatherForecastDto

    companion object {
        const val FORECAST_ENDPOINT_PATH = "v1/forecast"

        /**
         * A list of hourly weather variables which should be returned.
         */
        val FORECAST_HOURLY_PARAMS = listOf(
            "temperature_2m",
            "weathercode",
            "relativehumidity_2m",
            "windspeed_10m",
            "pressure_msl"
        )
    }
}
