package com.androidgeek.weather.util

import com.androidgeek.weather.util.FileReaderFromLocalTestResources.readBinaryFile
import com.androidgeek.weather.util.FileReaderFromLocalTestResources.readFile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class FileReaderFromLocalTestResourcesTest {

    private val fileName = "api-response/forecast-400.json"
    private val expectedResult = """
        {
          "error": true,
          "reason": "Cannot initialize WeatherVariable from invalid String value tempeture_2m for key hourly"
        }
    """.trimIndent()

    @Test
    fun testFileReading() {
        assertEquals(expectedResult, readFile(fileName).trimIndent())
    }

    @Test
    fun testBinaryFileReading() {
        assertEquals(expectedResult, String(readBinaryFile(fileName)).trimIndent())
    }

}
