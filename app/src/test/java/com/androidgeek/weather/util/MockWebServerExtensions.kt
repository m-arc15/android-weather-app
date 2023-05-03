package com.androidgeek.weather.util

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.io.BufferedReader
import java.util.concurrent.TimeUnit

fun MockWebServer.enqueueResponse(
    fileName: String,
    responseCode: Int,
    delayInMillis: Long = 0
) {
    val body = readFileFromResources("api-response/$fileName")
    val response = MockResponse().setResponseCode(responseCode).setBody(body)
        .setBodyDelay(delayInMillis, TimeUnit.MILLISECONDS)
    enqueue(response)
}

private fun readFileFromResources(fileName: String): String {
    return getBufferedReaderFromResource(fileName)?.use { it.readText() } ?: ""
}

private fun getBufferedReaderFromResource(fileName: String): BufferedReader? {
    return ClassLoader.getSystemResourceAsStream(fileName)?.bufferedReader()
}
