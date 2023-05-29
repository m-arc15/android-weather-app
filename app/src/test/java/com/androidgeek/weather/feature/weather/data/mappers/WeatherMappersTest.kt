package com.androidgeek.weather.feature.weather.data.mappers

import com.androidgeek.weather.feature.weather.data.DataFixtures
import com.androidgeek.weather.feature.weather.data.remote.model.WeatherForecastDataDto
import com.androidgeek.weather.feature.weather.domain.DomainFixtures.NOW
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherData
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType.Companion.fromWMO
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class WeatherMappersTest {

    @DisplayName("""
        GIVEN a forecast response with no data
        WHEN we call toWeatherDataPerDay() mapper
        THEN it should return empty map
    """)
    @Test
    fun toWeatherDataPerDay_emptyInput_returnsEmptyMap() {
        val data = DataFixtures.getWeatherForecastDataDto()

        val weeklyForecast = data.toWeatherDataPerDay()

        assertThat(weeklyForecast).isEmpty()
    }

    @DisplayName("""
        GIVEN a forecast response with seven days data
        WHEN we call toWeatherDataPerDay() mapper
        THEN it should return map with seven days data
    """)
    @Test
    fun toWeatherDataPerDay_validInputWithMultipleDayData_returnsMapWithMultipleDaysData() {
        val expectedNumberOfDays = DAYS_PER_WEEK
        val forecastDataDto = DataFixtures.generateWeatherForecastDataFor(expectedNumberOfDays)

        val forecastMap = forecastDataDto.toWeatherDataPerDay()

        assertThat(forecastMap.size).isEqualTo(expectedNumberOfDays)
        assertWeatherContentEquals(forecastMap, forecastDataDto)
    }

    @DisplayName("""
        GIVEN a forecast response with one day data
        WHEN we call toWeatherDataPerDay() mapper
        THEN it should return map with one day data
    """)
    @Test
    fun toWeatherDataPerDay_validInputWithOneDayData_returnsMapWithOneDayData() {
        val expectedNumberOfDays = 1
        val forecastDataDto = DataFixtures.generateWeatherForecastDataFor(expectedNumberOfDays)

        val forecastMap = forecastDataDto.toWeatherDataPerDay()

        assertThat(forecastMap.size).isEqualTo(expectedNumberOfDays)
        assertThat(forecastMap[0]!!.size).isEqualTo(HOURS_PER_DAY)
        assertWeatherContentEquals(forecastMap, forecastDataDto)
    }

    @DisplayName("""
        GIVEN a forecast response with missing data
        WHEN we call toWeatherDataPerDay() mapper
        THEN it should throw an IndexOutOfBoundsException
    """)
    @Test
    fun toWeatherDataPerDay_inputWithMissingData_throwsException() {
        val forecastDataDto = WeatherForecastDataDto(
            times = listOf("2023-05-01T01:00", "2023-05-01T02:00"),
            temperatures = listOf(5.0), // <== missing temperature here!!
            weatherCodes = listOf(0, 0),
            pressures = listOf(1010.0, 1020.0),
            windSpeeds = listOf(4.0, 7.0),
            humidities = listOf(80.0, 81.0)
        )

        val block: () -> Unit = { forecastDataDto.toWeatherDataPerDay() }

        assertThrows<IndexOutOfBoundsException>(block)
    }


    private fun assertWeatherContentEquals(
            forecastMap: Map<Int, List<WeatherData>>,
            forecastDataDto: WeatherForecastDataDto
    ) {
        // verify number of days
        val numberOfDays = forecastMap.size
        assertThat(numberOfDays).isEqualTo(forecastDataDto.times.size / HOURS_PER_DAY)

        // verify weather data
        for (i in 0 until numberOfDays * HOURS_PER_DAY) {
            val dayIdx = i / HOURS_PER_DAY
            val hourIdx = i % HOURS_PER_DAY
            val dailyForecast = forecastMap[dayIdx]!!
            val weatherData = dailyForecast[hourIdx]
            assertThat(weatherData.time.toString()).isEqualTo(forecastDataDto.times[i])
            assertThat(weatherData.temperatureCelsius).isEqualTo(forecastDataDto.temperatures[i])
            assertThat(weatherData.weatherType).isEqualTo(fromWMO(forecastDataDto.weatherCodes[i]))
            assertThat(weatherData.pressure).isEqualTo(forecastDataDto.pressures[i])
            assertThat(weatherData.windSpeed).isEqualTo(forecastDataDto.windSpeeds[i])
            assertThat(weatherData.humidity).isEqualTo(forecastDataDto.humidities[i])
        }
    }

    @DisplayName("""
        GIVEN a forecast response with no data
        WHEN we call toWeatherInfo() mapper
        THEN it should return empty map with null current weather data
    """)
    @Test
    fun toWeatherInfo_emptyInput_returnsEmptyMapWithNullWeatherData() {
        val forecastDto = DataFixtures.getWeatherForecastDto()

        val weatherInfo = forecastDto.toWeatherInfo()

        assertThat(weatherInfo.weatherDataPerDay).isEmpty()
        assertThat(weatherInfo.currentWeatherData).isNull()
    }

    @DisplayName("""
        GIVEN a forecast response with data and clock shows less than 30 minutes
        WHEN we call toWeatherInfo() mapper
        THEN it should return weather data for current hour
    """)
    @Test
    fun toWeatherInfo_clockShowsLessThanThirtyMinutes_returnsWeatherDataForCurrentHour() {
        val forecastDto = DataFixtures.getWeatherForecastDto(
            weatherForecastData = DataFixtures.generateWeatherForecastDataFor(DAYS_PER_WEEK)
        )
        val currentTime = NOW.withMinute(29)

        val weatherInfo = forecastDto.toWeatherInfo(now = currentTime)

        assertThat(weatherInfo.weatherDataPerDay).isNotEmpty()
        val currentWeather = weatherInfo.weatherDataPerDay[0]!![currentTime.hour]
        assertThat(weatherInfo.currentWeatherData).isEqualTo(currentWeather)
    }

    @DisplayName("""
        GIVEN a forecast response with data and clock shows 30 or more minutes
        WHEN we call toWeatherInfo() mapper
        THEN it should return weather data for next hour
    """)
    @Test
    fun toWeatherInfo_clockShowsThirtyOrMoreMinutes_returnsWeatherDataForNextHour() {
        val forecastDto = DataFixtures.getWeatherForecastDto(
            weatherForecastData = DataFixtures.generateWeatherForecastDataFor(DAYS_PER_WEEK)
        )
        val currentTime = NOW.withMinute(30)

        val weatherInfo = forecastDto.toWeatherInfo(now = currentTime)

        assertThat(weatherInfo.weatherDataPerDay).isNotEmpty()
        val currentWeather = weatherInfo.weatherDataPerDay[0]!![currentTime.hour+1]
        assertThat(weatherInfo.currentWeatherData).isEqualTo(currentWeather)
    }

    @DisplayName("""
        GIVEN a forecast response with data and clock shows 23 hour with 30 or more minutes
        WHEN we call toWeatherInfo() mapper
        THEN it should return first weather data for next day
    """)
    @Test
    fun toWeatherInfo_clockShowsLastHourWithThirtyOrMoreMinutes_returnsFirstWeatherDataForNextDay() {
        val forecastDto = DataFixtures.getWeatherForecastDto(
            weatherForecastData = DataFixtures.generateWeatherForecastDataFor(DAYS_PER_WEEK)
        )
        val currentTime = NOW.withHour(23).withMinute(30)

        val weatherInfo = forecastDto.toWeatherInfo(now = currentTime)

        assertThat(weatherInfo.weatherDataPerDay).isNotEmpty()
        val currentWeather = weatherInfo.weatherDataPerDay[1]!![0]
        assertThat(weatherInfo.currentWeatherData).isEqualTo(currentWeather)
    }

}

private const val HOURS_PER_DAY = 24
private const val DAYS_PER_WEEK = 7
