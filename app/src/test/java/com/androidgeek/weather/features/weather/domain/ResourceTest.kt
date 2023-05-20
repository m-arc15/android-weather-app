package com.androidgeek.weather.features.weather.domain

import com.androidgeek.weather.features.weather.domain.util.Resource
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


internal class ResourceTest {

    private lateinit var resource: Resource<*>

    @Test
    fun testSuccessResource() {
        resource = Resource.Success(100)

        assertTrue(resource is Resource.Success)
        assertEquals(100, resource.data)
        assertNull(resource.message)
    }

    @Test
    fun testErrorResource() {
        resource = Resource.Error<Int>("Invalid request")

        assertTrue(resource is Resource.Error)
        assertNull(resource.data)
        assertEquals("Invalid request", resource.message)
    }
}
