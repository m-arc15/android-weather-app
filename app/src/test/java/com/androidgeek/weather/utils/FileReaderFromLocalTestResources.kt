package com.androidgeek.weather.utils

import java.io.ByteArrayOutputStream
import java.io.InputStream

object FileReaderFromLocalTestResources {

    fun readFile(fileName: String): String {
        return getInputStreamFromResource(fileName)?.bufferedReader()?.use { it.readText() } ?: ""
    }

    fun readBinaryFile(fileName: String): ByteArray {
        ByteArrayOutputStream().use { byteStream ->
            getInputStreamFromResource(fileName)?.copyTo(byteStream)
            return byteStream.toByteArray()
        }
    }

    private fun getInputStreamFromResource(fileName: String): InputStream? {
        return ClassLoader.getSystemResourceAsStream(fileName)
    }

}
