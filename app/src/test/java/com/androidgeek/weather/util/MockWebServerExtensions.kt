package com.androidgeek.weather.util

import com.androidgeek.weather.util.FileReaderFromLocalTestResources.readBinaryFile
import com.androidgeek.weather.util.FileReaderFromLocalTestResources.readFile
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.util.concurrent.TimeUnit

fun MockWebServer.enqueueResponse(
    fileName: String,
    responseCode: Int,
    delayInMillis: Long = 0
) {
    val body = readFile("api-response/$fileName")
    val response = MockResponse()
        .setResponseCode(responseCode)
        .setBody(body)
        .setBodyDelay(delayInMillis, TimeUnit.MILLISECONDS)
    enqueue(response)
}

fun MockWebServer.enqueueResponseFromBinaryFile(
        fileName: String,
        responseCode: Int,
        delayInMillis: Long = 0
) {
    val body = String(readBinaryFile("api-response/$fileName"))
    val response = MockResponse()
        .setResponseCode(responseCode)
        .setBody(body)
        .setBodyDelay(delayInMillis, TimeUnit.MILLISECONDS)
    enqueue(response)
}

