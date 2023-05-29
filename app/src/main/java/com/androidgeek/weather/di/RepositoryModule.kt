package com.androidgeek.weather.di

import com.androidgeek.weather.feature.weather.data.repository.LocationRepositoryImpl
import com.androidgeek.weather.feature.weather.data.repository.WeatherRepositoryImpl
import com.androidgeek.weather.feature.weather.domain.location.repository.LocationRepository
import com.androidgeek.weather.feature.weather.domain.weather.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLocationRepo(repo: LocationRepositoryImpl): LocationRepository

    @Binds
    @Singleton
    abstract fun bindWeatherRepo(repo: WeatherRepositoryImpl): WeatherRepository

}
