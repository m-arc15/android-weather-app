package com.androidgeek.weather.util

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.io.ByteArrayOutputStream
import java.io.InputStream
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

fun MockWebServer.enqueueResponseFromBinaryFile(
        fileName: String,
        responseCode: Int,
        delayInMillis: Long = 0
) {
    val body = String(readBinaryFileFromResources("api-response/$fileName"))
    val response = MockResponse().setResponseCode(responseCode).setBody(body)
        .setBodyDelay(delayInMillis, TimeUnit.MILLISECONDS)
    enqueue(response)
}

private fun readFileFromResources(fileName: String): String {
    return getInputStreamFromResource(fileName)?.bufferedReader()?.use { it.readText() } ?: ""
}

private fun readBinaryFileFromResources(fileName: String): ByteArray {
    ByteArrayOutputStream().use { byteStream ->
        getInputStreamFromResource(fileName)?.copyTo(byteStream)
        return byteStream.toByteArray()
    }
}

private fun getInputStreamFromResource(fileName: String): InputStream? {
    return ClassLoader.getSystemResourceAsStream(fileName)
}
