package com.androidgeek.weather.di

import android.app.Application
import com.androidgeek.weather.core.Utils
import com.androidgeek.weather.core.Utils.createHttpClient
import com.androidgeek.weather.feature.weather.data.remote.api.OpenMeteoWeatherForecastApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(
            app: Application
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return createHttpClient()
    }

    @Provides
    @Singleton
    fun provideWeatherApi(
            httpClient: OkHttpClient
    ): OpenMeteoWeatherForecastApi {
        return Utils.createRetrofitApi(
            baseUrl = "https://api.open-meteo.com/",
            httpClient = httpClient,
            apiClass = OpenMeteoWeatherForecastApi::class.java
        )
    }

}
