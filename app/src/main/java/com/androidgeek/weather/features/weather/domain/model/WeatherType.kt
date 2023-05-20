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

    object LightFreezingRain : WeatherType("Light freezing rain", R.drawable.ic_snowyrainy)
    object HeavyFreezingRain : WeatherType("Heavy freezing rain", R.drawable.ic_snowyrainy)

    object SlightSnowFall : WeatherType("Slight snow fall", R.drawable.ic_snowy)
    object ModerateSnowFall : WeatherType("Moderate snow fall", R.drawable.ic_heavysnow)
    object HeavySnowFall : WeatherType("Heavy snow fall", R.drawable.ic_heavysnow)

    object SnowGrains : WeatherType("Snow grains", R.drawable.ic_heavysnow)

    object SlightRainShowers : WeatherType("Slight rain showers", R.drawable.ic_rainshower)
    object ModerateRainShowers : WeatherType("Moderate rain showers", R.drawable.ic_rainshower)
    object ViolentRainShowers : WeatherType("Violent rain showers", R.drawable.ic_rainshower)

    object SlightSnowShowers : WeatherType("Slight snow showers", R.drawable.ic_snowy)
    object HeavySnowShowers : WeatherType("Heavy snow showers", R.drawable.ic_snowy)

    object ModerateThunderstorm : WeatherType("Moderate thunderstorm", R.drawable.ic_thunder)
    object SlightHailThunderstorm :
        WeatherType("Thunderstorm with slight hail", R.drawable.ic_rainythunder)

    object HeavyHailThunderstorm :
        WeatherType("Thunderstorm with heavy hail", R.drawable.ic_rainythunder)

    object UNREACHABLE : WeatherType("Unreachable weather type", R.drawable.ic_launcher_background)

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
                else -> UNREACHABLE
            }

            fun fromFoggy(code: Int) = when (code) {
                45 -> Foggy
                48 -> DepositingRimeFog
                else -> UNREACHABLE
            }

            fun fromDrizzle(code: Int) = when (code) {
                51 -> LightDrizzle
                53 -> ModerateDrizzle
                55 -> DenseDrizzle
                else -> UNREACHABLE
            }

            fun fromFreezingDrizzle(code: Int) = when (code) {
                56 -> LightFreezingDrizzle
                57 -> DenseFreezingDrizzle
                else -> UNREACHABLE
            }

            fun fromRain(code: Int) = when (code) {
                61 -> SlightRain
                63 -> ModerateRain
                65 -> HeavyRain
                else -> UNREACHABLE
            }

            fun fromFreezingRain(code: Int) = when (code) {
                66 -> LightFreezingRain
                67 -> HeavyFreezingRain
                else -> UNREACHABLE
            }

            fun fromSnowFall(code: Int) = when (code) {
                71 -> SlightSnowFall
                73 -> ModerateSnowFall
                75 -> HeavySnowFall
                else -> UNREACHABLE
            }

            fun fromRainShowers(code: Int) = when (code) {
                80 -> SlightRainShowers
                81 -> ModerateRainShowers
                82 -> ViolentRainShowers
                else -> UNREACHABLE
            }

            fun fromSnowShowers(code: Int) = when (code) {
                85 -> SlightSnowShowers
                86 -> HeavySnowShowers
                else -> UNREACHABLE
            }

            fun fromThunderstorm(code: Int) = when (code) {
                95 -> ModerateThunderstorm
                96 -> SlightHailThunderstorm
                99 -> HeavyHailThunderstorm
                else -> UNREACHABLE
            }

            return when (code) {
                0 -> ClearSky
                1, 2, 3 -> fromCloudy(code)
                45, 48 -> fromFoggy(code)
                51, 53, 55 -> fromDrizzle(code)
                56, 57 -> fromFreezingDrizzle(code)
                61, 63, 65 -> fromRain(code)
                66, 67 -> fromFreezingRain(code)
                71, 73, 75 -> fromSnowFall(code)
                77 -> SnowGrains
                80, 81, 82 -> fromRainShowers(code)
                85, 86 -> fromSnowShowers(code)
                95, 96, 99 -> fromThunderstorm(code)
                else -> DEFAULT
            }
        }
    }
}
