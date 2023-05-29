package com.androidgeek.weather.feature.weather.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.androidgeek.weather.feature.weather.domain.location.repository.LocationRepository
import com.androidgeek.weather.feature.weather.domain.weather.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.androidgeek.weather.feature.weather.domain.util.Resource
import kotlinx.coroutines.launch

@HiltViewModel
class WeatherViewModel @Inject constructor(
        private val weatherRepo: WeatherRepository,
        private val locationRepo: LocationRepository
): ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )

            locationRepo.getCurrentLocation()?.let { location ->
                when(val result = weatherRepo.getWeatherData(location.lat, location.long)) {
                    is Resource.Success -> {
                        state = state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: run {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                )
            }
        }
    }

}
