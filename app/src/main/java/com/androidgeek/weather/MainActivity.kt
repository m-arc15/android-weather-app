package com.androidgeek.weather

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherData
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherInfo
import com.androidgeek.weather.feature.weather.domain.weather.model.WeatherType
import com.androidgeek.weather.feature.weather.presentation.WeatherCard
import com.androidgeek.weather.feature.weather.presentation.WeatherForecast
import com.androidgeek.weather.feature.weather.presentation.WeatherState
import com.androidgeek.weather.feature.weather.presentation.WeatherViewModel
import com.androidgeek.weather.ui.theme.DarkBlue
import com.androidgeek.weather.ui.theme.DeepBlue
import com.androidgeek.weather.ui.theme.WeatherTheme
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.loadWeatherInfo()
        }

        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        setContent {
            WeatherTheme {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(DarkBlue)
                    ) {
                        WeatherCard(state = viewModel.state, backgroundColor = DeepBlue)
                        Spacer(modifier = Modifier.height(16.dp))
                        WeatherForecast(state = viewModel.state)
                    }

                    if (viewModel.state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    viewModel.state.error?.let { error ->
                        Text(
                            text = error,
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(
        state: WeatherState,
        backgroundColor: Color,
        modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
    ) {
        WeatherCard(state = state, backgroundColor = DeepBlue, modifier = modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherTheme {
        MainScreen(
            state = WeatherState(
                WeatherInfo(
                    weatherDataPerDay = emptyMap(),
                    currentWeatherData = WeatherData(
                        time = LocalDateTime.now(),
                        temperatureCelsius = 18.0,
                        pressure = 1018.0,
                        windSpeed = 15.0,
                        humidity = 78.0,
                        weatherType = WeatherType.ModerateRain
                    )
                )
            ), backgroundColor = DeepBlue
        )
    }
}
