package com.androidgeek.weather.feature.weather.domain

import com.androidgeek.weather.feature.weather.domain.util.Resource
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test


internal class ResourceTest {

    private lateinit var resource: Resource<*>

    @Test
    fun testSuccessResource() {
        resource = Resource.Success(100)

        assertThat(resource).isInstanceOf(Resource.Success::class.java)
        assertEquals(100, resource.data)
        assertNull(resource.message)
    }

    @Test
    fun testErrorResource() {
        resource = Resource.Error<Int>("Invalid request")

        assertThat(resource).isInstanceOf(Resource.Error::class.java)
        assertNull(resource.data)
        assertEquals("Invalid request", resource.message)
    }
}
