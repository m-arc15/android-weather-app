package com.androidgeek.weather.feature.weather.domain.model

import androidx.annotation.DrawableRes
import com.androidgeek.weather.R
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.ClearSky
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.Companion.DEFAULT
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.Companion.fromWMO
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.DenseDrizzle
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.DenseFreezingDrizzle
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.DepositingRimeFog
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.Foggy
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.HeavyFreezingRain
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.HeavyHailThunderstorm
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.HeavyRain
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.HeavySnowFall
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.HeavySnowShowers
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.LightDrizzle
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.LightFreezingDrizzle
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.LightFreezingRain
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.MainlyClear
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.ModerateDrizzle
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.ModerateRain
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.ModerateRainShowers
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.ModerateSnowFall
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.ModerateThunderstorm
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.Overcast
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.PartlyCloudy
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.SlightHailThunderstorm
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.SlightRain
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.SlightRainShowers
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.SlightSnowFall
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.SlightSnowShowers
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.SnowGrains
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.ViolentRainShowers
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

internal class WeatherTypeTest {

    @Test
    fun fromWMO_code0_returnsClearSky() {
        assertThat(fromWMO(0)).isEqualTo(ClearSky)
    }

    @Test
    fun testClearSky() {
        ClearSky.assertValue(
            weatherDesc = "Clear sky",
            iconRes = R.drawable.ic_sunny
        )
    }

    @Test
    fun fromWMO_unsupportedCode_returnsDefault() {
        val unsupportedCode = Int.MIN_VALUE
        assertThat(fromWMO(unsupportedCode)).isEqualTo(DEFAULT)
    }

    @Test
    fun testDefaultIsEqualToClearSky() {
        assertThat(DEFAULT).isEqualTo(ClearSky)
    }

    @Test
    fun fromWMO_code1_returnsMainlyClear() {
        assertThat(fromWMO(1)).isEqualTo(MainlyClear)
    }

    @Test
    fun testMainlyClear() {
        MainlyClear.assertValue(
            weatherDesc = "Mainly clear",
            iconRes = R.drawable.ic_cloudy
        )
    }

    @Test
    fun fromWMO_code2_returnsPartlyCloudy() {
        assertThat(fromWMO(2)).isEqualTo(PartlyCloudy)
    }

    @Test
    fun testPartlyCloudy() {
        PartlyCloudy.assertValue(
            weatherDesc = "Partly cloudy",
            iconRes = R.drawable.ic_cloudy
        )
    }

    @Test
    fun fromWMO_code3_returnsOvercast() {
        assertThat(fromWMO(3)).isEqualTo(Overcast)
    }

    @Test
    fun testOvercast() {
        Overcast.assertValue(
            weatherDesc = "Overcast",
            iconRes = R.drawable.ic_cloudy
        )
    }

    @Test
    fun fromWMO_code45_returnsFoggy() {
        assertThat(fromWMO(45)).isEqualTo(Foggy)
    }

    @Test
    fun testFoggy() {
        Foggy.assertValue(
            weatherDesc = "Foggy",
            iconRes = R.drawable.ic_very_cloudy
        )
    }

    @Test
    fun fromWMO_code48_returnsDepositingRimeFog() {
        assertThat(fromWMO(48)).isEqualTo(DepositingRimeFog)
    }

    @Test
    fun testDepositingRimeFog() {
        DepositingRimeFog.assertValue(
            weatherDesc = "Depositing rime fog",
            iconRes = R.drawable.ic_very_cloudy
        )
    }

    @Test
    fun fromWMO_code51_returnsLightDrizzle() {
        assertThat(fromWMO(51)).isEqualTo(LightDrizzle)
    }

    @Test
    fun testLightDrizzle() {
        LightDrizzle.assertValue(
            weatherDesc = "Light drizzle",
            iconRes = R.drawable.ic_rainshower
        )
    }

    @Test
    fun fromWMO_code53_returnsModerateDrizzle() {
        assertThat(fromWMO(53)).isEqualTo(ModerateDrizzle)
    }

    @Test
    fun testModerateDrizzle() {
        ModerateDrizzle.assertValue(
            weatherDesc = "Moderate drizzle",
            iconRes = R.drawable.ic_rainshower
        )
    }

    @Test
    fun fromWMO_code55_returnsDenseDrizzle() {
        assertThat(fromWMO(55)).isEqualTo(DenseDrizzle)
    }

    @Test
    fun testDenseDrizzle() {
        DenseDrizzle.assertValue(
            weatherDesc = "Dense drizzle",
            iconRes = R.drawable.ic_rainshower
        )
    }

    @Test
    fun fromWMO_code56_returnsLightFreezingDrizzle() {
        assertThat(fromWMO(56)).isEqualTo(LightFreezingDrizzle)
    }

    @Test
    fun testLightFreezingDrizzle() {
        LightFreezingDrizzle.assertValue(
            weatherDesc = "Light freezing drizzle",
            iconRes = R.drawable.ic_snowyrainy
        )
    }

    @Test
    fun fromWMO_code57_returnsDenseFreezingDrizzle() {
        assertThat(fromWMO(57)).isEqualTo(DenseFreezingDrizzle)
    }

    @Test
    fun testDenseFreezingDrizzle() {
        DenseFreezingDrizzle.assertValue(
            weatherDesc = "Dense freezing drizzle",
            iconRes = R.drawable.ic_snowyrainy
        )
    }

    @Test
    fun fromWMO_code61_returnsSlightRain() {
        assertThat(fromWMO(61)).isEqualTo(SlightRain)
    }

    @Test
    fun testSlightRain() {
        SlightRain.assertValue(
            weatherDesc = "Slight rain",
            iconRes = R.drawable.ic_rainy
        )
    }

    @Test
    fun fromWMO_code63_returnsModerateRain() {
        assertThat(fromWMO(63)).isEqualTo(ModerateRain)
    }

    @Test
    fun testModerateRain() {
        ModerateRain.assertValue(
            weatherDesc = "Moderate rain",
            iconRes = R.drawable.ic_rainy
        )
    }

    @Test
    fun fromWMO_code65_returnsHeavyRain() {
        assertThat(fromWMO(65)).isEqualTo(HeavyRain)
    }

    @Test
    fun testHeavyRain() {
        HeavyRain.assertValue(
            weatherDesc = "Heavy rain",
            iconRes = R.drawable.ic_rainy
        )
    }

    @Test
    fun fromWMO_code66_returnsLightFreezingRain() {
        assertThat(fromWMO(66)).isEqualTo(LightFreezingRain)
    }

    @Test
    fun testLightFreezingRain() {
        LightFreezingRain.assertValue(
            weatherDesc = "Light freezing rain",
            iconRes = R.drawable.ic_snowyrainy
        )
    }

    @Test
    fun fromWMO_code67_returnsHeavyFreezingRain() {
        assertThat(fromWMO(67)).isEqualTo(HeavyFreezingRain)
    }

    @Test
    fun testHeavyFreezingRain() {
        HeavyFreezingRain.assertValue(
            weatherDesc = "Heavy freezing rain",
            iconRes = R.drawable.ic_snowyrainy
        )
    }

    @Test
    fun fromWMO_code71_returnsSlightSnowFall() {
        assertThat(fromWMO(71)).isEqualTo(SlightSnowFall)
    }

    @Test
    fun testSlightSnowFall() {
        SlightSnowFall.assertValue(
            weatherDesc = "Slight snow fall",
            iconRes = R.drawable.ic_snowy
        )
    }

    @Test
    fun fromWMO_code73_returnsModerateSnowFall() {
        assertThat(fromWMO(73)).isEqualTo(ModerateSnowFall)
    }

    @Test
    fun testModerateSnowFall() {
        ModerateSnowFall.assertValue(
            weatherDesc = "Moderate snow fall",
            iconRes = R.drawable.ic_heavysnow
        )
    }

    @Test
    fun fromWMO_code75_returnsHeavySnowFall() {
        assertThat(fromWMO(75)).isEqualTo(HeavySnowFall)
    }

    @Test
    fun testHeavySnowFall() {
        HeavySnowFall.assertValue(
            weatherDesc = "Heavy snow fall",
            iconRes = R.drawable.ic_heavysnow
        )
    }

    @Test
    fun fromWMO_code77_returnsSnowGrains() {
        assertThat(fromWMO(77)).isEqualTo(SnowGrains)
    }

    @Test
    fun testSnowGrains() {
        SnowGrains.assertValue(
            weatherDesc = "Snow grains",
            iconRes = R.drawable.ic_heavysnow
        )
    }

    @Test
    fun fromWMO_code80_returnsSlightRainShowers() {
        assertThat(fromWMO(80)).isEqualTo(SlightRainShowers)
    }

    @Test
    fun testSlightRainShowers() {
        SlightRainShowers.assertValue(
            weatherDesc = "Slight rain showers",
            iconRes = R.drawable.ic_rainshower
        )
    }

    @Test
    fun fromWMO_code81_returnsModerateRainShowers() {
        assertThat(fromWMO(81)).isEqualTo(ModerateRainShowers)
    }

    @Test
    fun testModerateRainShowers() {
        ModerateRainShowers.assertValue(
            weatherDesc = "Moderate rain showers",
            iconRes = R.drawable.ic_rainshower
        )
    }

    @Test
    fun fromWMO_code82_returnsViolentRainShowers() {
        assertThat(fromWMO(82)).isEqualTo(ViolentRainShowers)
    }

    @Test
    fun testViolentRainShowers() {
        ViolentRainShowers.assertValue(
            weatherDesc = "Violent rain showers",
            iconRes = R.drawable.ic_rainshower
        )
    }

    @Test
    fun fromWMO_code85_returnsSlightSnowShowers() {
        assertThat(fromWMO(85)).isEqualTo(SlightSnowShowers)
    }

    @Test
    fun testSlightSnowShowers() {
        SlightSnowShowers.assertValue(
            weatherDesc = "Slight snow showers",
            iconRes = R.drawable.ic_snowy
        )
    }

    @Test
    fun fromWMO_code86_returnsHeavySnowShowers() {
        assertThat(fromWMO(86)).isEqualTo(HeavySnowShowers)
    }

    @Test
    fun testHeavySnowShowers() {
        HeavySnowShowers.assertValue(
            weatherDesc = "Heavy snow showers",
            iconRes = R.drawable.ic_snowy
        )
    }

    @Test
    fun fromWMO_code95_returnsModerateThunderstorm() {
        assertThat(fromWMO(95)).isEqualTo(ModerateThunderstorm)
    }

    @Test
    fun testModerateThunderstorm() {
        ModerateThunderstorm.assertValue(
            weatherDesc = "Moderate thunderstorm",
            iconRes = R.drawable.ic_thunder
        )
    }

    @Test
    fun fromWMO_code96_returnsSlightHailThunderstorm() {
        assertThat(fromWMO(96)).isEqualTo(SlightHailThunderstorm)
    }

    @Test
    fun testSlightHailThunderstorm() {
        SlightHailThunderstorm.assertValue(
            weatherDesc = "Thunderstorm with slight hail",
            iconRes = R.drawable.ic_rainythunder
        )
    }

    @Test
    fun fromWMO_code99_returnsHeavyHailThunderstorm() {
        assertThat(fromWMO(99)).isEqualTo(HeavyHailThunderstorm)
    }

    @Test
    fun testHeavyHailThunderstorm() {
        HeavyHailThunderstorm.assertValue(
            "Thunderstorm with heavy hail",
            R.drawable.ic_rainythunder
        )
    }

    private fun WeatherType.assertValue(weatherDesc: String, @DrawableRes iconRes: Int) {
        assertThat(this.weatherDesc).isEqualTo(weatherDesc)
        assertThat(this.iconRes).isEqualTo(iconRes)
    }
}
