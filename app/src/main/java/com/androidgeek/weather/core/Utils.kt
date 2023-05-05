package com.androidgeek.weather.core

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object Utils {
    private val json = Json(builderAction = { ignoreUnknownKeys = true })
    private val contentType = "application/json; charset=utf-8".toMediaType()
    private fun createConverterFactory() = json.asConverterFactory(contentType)

    fun createHttpClient(
        timeout: Long = 10_000,
        unit: TimeUnit = TimeUnit.MILLISECONDS
    ) = OkHttpClient.Builder()
        .connectTimeout(timeout, unit)
        .readTimeout(timeout, unit)
        .writeTimeout(timeout, unit)
        .addInterceptor(Interceptor { chain ->  
            val original = chain.request()
            val request = original.newBuilder()
                .addHeader("User-Agent", "WeatherApp")
                .addHeader("Accept-Language", "en-GB")
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        })
        .build()

    fun <T> createRetrofitApi(
        baseUrl: HttpUrl,
        httpClient: OkHttpClient,
        apiClass: Class<T>
    ): T = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(createConverterFactory())
        .build()
        .create(apiClass)
}
