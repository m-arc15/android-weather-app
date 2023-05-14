package com.androidgeek.weather.features.weather.domain.model

import androidx.annotation.DrawableRes
import com.androidgeek.weather.R
import com.androidgeek.weather.features.weather.domain.model.WeatherType.Companion.fromWMO
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

internal class WeatherTypeTest {

    @Test
    fun fromWMO_code0_returnsClearSky() {
        fromWMO(0).assertValue(
            weatherDesc = "Clear sky",
            iconRes = R.drawable.ic_sunny
        )
    }

    @Test
    fun fromWMO_unsupportedCode_returnsDefaultClearSky() {
        val unsupportedCode = Int.MIN_VALUE
        val defaultWeatherType = fromWMO(unsupportedCode)

        assertThat(defaultWeatherType).isEqualTo(WeatherType.DEFAULT)
        defaultWeatherType.assertValue(
            weatherDesc = "Clear sky",
            iconRes = R.drawable.ic_sunny
        )
    }

    @Test
    fun fromWMO_code1_returnsMainlyClear() {
        fromWMO(1).assertValue(
            weatherDesc = "Mainly clear",
            iconRes = R.drawable.ic_cloudy
        )
    }

    @Test
    fun fromWMO_code2_returnsPartlyCloudy() {
        fromWMO(2).assertValue(
            weatherDesc = "Partly cloudy",
            iconRes = R.drawable.ic_cloudy
        )
    }

    @Test
    fun fromWMO_code3_returnsOvercast() {
        fromWMO(3).assertValue(
            weatherDesc = "Overcast",
            iconRes = R.drawable.ic_cloudy
        )
    }

    @Test
    fun fromWMO_code45_returnsFoggy() {
        fromWMO(45).assertValue(
            weatherDesc = "Foggy",
            iconRes = R.drawable.ic_very_cloudy
        )
    }

    @Test
    fun fromWMO_code48_returnsDepositingRimeFog() {
        fromWMO(48).assertValue(
            weatherDesc = "Depositing rime fog",
            iconRes = R.drawable.ic_very_cloudy
        )
    }

    @Test
    fun fromWMO_code51_returnsLightDrizzle() {
        fromWMO(51).assertValue(
            weatherDesc = "Light drizzle",
            iconRes = R.drawable.ic_rainshower
        )
    }

    @Test
    fun fromWMO_code53_returnsModerateDrizzle() {
        fromWMO(53).assertValue(
            weatherDesc = "Moderate drizzle",
            iconRes = R.drawable.ic_rainshower
        )
    }

    @Test
    fun fromWMO_code55_returnsDenseDrizzle() {
        fromWMO(55).assertValue(
            weatherDesc = "Dense drizzle",
            iconRes = R.drawable.ic_rainshower
        )
    }

    @Test
    fun fromWMO_code56_returnsLightFreezingDrizzle() {
        fromWMO(56).assertValue(
            weatherDesc = "Light freezing drizzle",
            iconRes = R.drawable.ic_snowyrainy
        )
    }

    @Test
    fun fromWMO_code57_returnsDenseFreezingDrizzle() {
        fromWMO(57).assertValue(
            weatherDesc = "Dense freezing drizzle",
            iconRes = R.drawable.ic_snowyrainy
        )
    }

    @Test
    fun fromWMO_code61_returnsSlightRain() {
        fromWMO(61).assertValue(
            weatherDesc = "Slight rain",
            iconRes = R.drawable.ic_rainy
        )
    }

    @Test
    fun fromWMO_code63_returnsModerateRain() {
        fromWMO(63).assertValue(
            weatherDesc = "Moderate rain",
            iconRes = R.drawable.ic_rainy
        )
    }

    @Test
    fun fromWMO_code65_returnsHeavyRain() {
        fromWMO(65).assertValue(
            weatherDesc = "Heavy rain",
            iconRes = R.drawable.ic_rainy
        )
    }

    @Test
    fun fromWMO_code66_returnsLightFreezingRain() {
        TODO()
    }

    @Test
    fun fromWMO_code67_returnsHeavyFreezingRain() {
        TODO()
    }

    @Test
    fun fromWMO_code71_returnsSlightSnowFall() {
        TODO()
    }

    @Test
    fun fromWMO_code73_returnsModerateSnowFall() {
        TODO()
    }

    @Test
    fun fromWMO_code75_returnsHeavySnowFall() {
        TODO()
    }

    @Test
    fun fromWMO_code77_returnsSnowGrains() {
        TODO()
    }

    @Test
    fun fromWMO_code80_returnsSlightRainShowers() {
        TODO()
    }

    @Test
    fun fromWMO_code81_returnsModerateRainShowers() {
        TODO()
    }

    @Test
    fun fromWMO_code82_returnsViolentRainShowers() {
        TODO()
    }

    @Test
    fun fromWMO_code85_returnsSlightSnowShowers() {
        TODO()
    }

    @Test
    fun fromWMO_code86_returnsHeavySnowShowers() {
        TODO()
    }

    @Test
    fun fromWMO_code95_returnsModerateThunderstorm() {
        TODO()
    }

    @Test
    fun fromWMO_code96_returnsSlightHailThunderstorm() {
        TODO()
    }

    @Test
    fun fromWMO_code99_returnsHeavyHailThunderstorm() {
        TODO()
    }

    private fun WeatherType.assertValue(weatherDesc: String, @DrawableRes iconRes: Int) {
        assertThat(this.weatherDesc).isEqualTo(weatherDesc)
        assertThat(this.iconRes).isEqualTo(iconRes)
    }
}
