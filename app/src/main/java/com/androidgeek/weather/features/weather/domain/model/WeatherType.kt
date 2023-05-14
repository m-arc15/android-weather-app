package com.androidgeek.weather.features.weather.domain.model

import androidx.annotation.DrawableRes
import com.androidgeek.weather.R

sealed class WeatherType(
        val weatherDesc: String,
        @DrawableRes val iconRes: Int
) {
    object ClearSky : WeatherType("Clear sky", R.drawable.ic_sunny)

    object MainlyClear : WeatherType("Mainly clear", R.drawable.ic_cloudy)
    object PartlyCloudy : WeatherType("Partly cloudy", R.drawable.ic_cloudy)
    object Overcast : WeatherType("Overcast", R.drawable.ic_cloudy)

    object Foggy : WeatherType("Foggy", R.drawable.ic_very_cloudy)
    object DepositingRimeFog : WeatherType("Depositing rime fog", R.drawable.ic_very_cloudy)

    object LightDrizzle : WeatherType("Light drizzle", R.drawable.ic_rainshower)
    object ModerateDrizzle : WeatherType("Moderate drizzle", R.drawable.ic_rainshower)
    object DenseDrizzle : WeatherType("Dense drizzle", R.drawable.ic_rainshower)

    object LightFreezingDrizzle : WeatherType("Light freezing drizzle", R.drawable.ic_snowyrainy)
    object DenseFreezingDrizzle : WeatherType("Dense freezing drizzle", R.drawable.ic_snowyrainy)

    object SlightRain : WeatherType("Slight rain", R.drawable.ic_rainy)
    object ModerateRain : WeatherType("Moderate rain", R.drawable.ic_rainy)
    object HeavyRain : WeatherType("Heavy rain", R.drawable.ic_rainy)

    companion object {

        val DEFAULT = ClearSky

        /**
         * Converts World Meteorological Organization codes to weather type with description and icon
         *
         * Weather variable documentation
         * WMO Weather interpretation codes (WW)
         * Code	    Description
         * 0	        Clear sky
         * 1, 2, 3	    Mainly clear, partly cloudy, and overcast
         * 45, 48	    Fog and depositing rime fog
         * 51, 53, 55	Drizzle: Light, moderate, and dense intensity
         * 56, 57	    Freezing Drizzle: Light and dense intensity
         * 61, 63, 65	Rain: Slight, moderate and heavy intensity
         * 66, 67	    Freezing Rain: Light and heavy intensity
         * 71, 73, 75	Snow fall: Slight, moderate, and heavy intensity
         * 77	        Snow grains
         * 80, 81, 82	Rain showers: Slight, moderate, and violent
         * 85, 86	    Snow showers slight and heavy
         * 95 *	    Thunderstorm: Slight or moderate
         * 96, 99 *	Thunderstorm with slight and heavy hail
         * (*) Thunderstorm forecast with hail is only available in Central Europe
         */
        fun fromWMO(code: Int): WeatherType {

            fun fromCloudy(code: Int) = when (code) {
                1 -> MainlyClear
                2 -> PartlyCloudy
                3 -> Overcast
                else -> DEFAULT
            }

            fun fromFoggy(code: Int) = when (code) {
                45 -> Foggy
                48 -> DepositingRimeFog
                else -> DEFAULT
            }

            fun fromDrizzle(code: Int) = when (code) {
                51 -> LightDrizzle
                53 -> ModerateDrizzle
                55 -> DenseDrizzle
                else -> DEFAULT
            }

            fun fromFreezingDrizzle(code: Int) = when(code) {
                56 -> LightFreezingDrizzle
                57 -> DenseFreezingDrizzle
                else -> DEFAULT
            }

            fun fromRain(code: Int) = when(code) {
                61 -> SlightRain
                63 -> ModerateRain
                65 -> HeavyRain
                else -> DEFAULT
            }

            return when (code) {
                0 -> ClearSky
                1, 2, 3 -> fromCloudy(code)
                45, 48 -> fromFoggy(code)
                51, 53, 55 -> fromDrizzle(code)
                56, 57 -> fromFreezingDrizzle(code)
                61, 63, 65 -> fromRain(code)
                else -> DEFAULT
            }
        }
    }
}
