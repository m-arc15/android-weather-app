package com.androidgeek.weather.feature.weather.data.repository

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnCanceledListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

typealias WeatherLocation = com.androidgeek.weather.feature.weather.domain.location.model.Location

@OptIn(ExperimentalCoroutinesApi::class)
internal class LocationRepositoryImplTest {

    private val location: Location = mockk {
        every { latitude } returns 10.0
        every { longitude } returns 20.0
    }
    private val locationTask: Task<Location> = mockk(relaxed = true) {
        every { isComplete } returns true
        every { isSuccessful } returns true
        every { result } returns location
    }
    private val locationManager: LocationManager = mockk {
        every { isProviderEnabled(any()) } returns true
    }

    private val locationClient: FusedLocationProviderClient = mockk {
        every { lastLocation } returns locationTask
    }
    private val app: Application = mockk {
        every { getSystemService(Context.LOCATION_SERVICE) } returns locationManager
        every { checkPermission(ACCESS_COARSE_LOCATION, any(), any()) } returns PERMISSION_GRANTED
        every { checkPermission(ACCESS_FINE_LOCATION, any(), any()) } returns PERMISSION_GRANTED
    }

    private val sut: LocationRepositoryImpl = LocationRepositoryImpl(locationClient, app)

    @Test
    fun getCurrentLocation_completeAndSuccess_returnsLocation() = runTest {
        val actual = sut.getCurrentLocation()

        assertThat(actual).isEqualTo(WeatherLocation(10.0, 20.0))
    }

    @Test
    fun getCurrentLocation_completeAndFailure_returnsNull() = runTest {
        every { locationTask.isSuccessful } returns false

        val actual = sut.getCurrentLocation()

        assertThat(actual).isNull()
    }

    @Test
    fun getCurrentLocation_notComplete_addsLocationTaskListeners() = runTest {
        every { locationTask.isComplete } returns false

        val job = TestScope(UnconfinedTestDispatcher()).launch { sut.getCurrentLocation() }

        runBlocking {
            job.cancel()
            job.join()
        }

        verify(exactly = 1) { locationTask.addOnSuccessListener(any()) }
        verify(exactly = 1) { locationTask.addOnFailureListener(any()) }
        verify(exactly = 1) { locationTask.addOnCanceledListener(any()) }
    }

    @Test
    fun getCurrentLocation_notCompleteAndSuccess_returnsLocation() = runTest {
        every { locationTask.isComplete } returns false
        val successSlot = slot<OnSuccessListener<Location>>()
        every { locationTask.addOnSuccessListener(capture(successSlot)) } answers {
            successSlot.captured.onSuccess(locationTask.result)
            locationTask
        }

        val actual = sut.getCurrentLocation()

        assertThat(actual).isEqualTo(WeatherLocation(10.0, 20.0))
    }

    @Test
    fun getCurrentLocation_failure_returnsNull() = runTest {
        every { locationTask.isComplete } returns false
        val failureSlot = slot<OnFailureListener>()
        every { locationTask.addOnFailureListener(capture(failureSlot)) } answers {
            failureSlot.captured.onFailure(RuntimeException("Failed to fetch location"))
            locationTask
        }

        val actual = sut.getCurrentLocation()

        assertThat(actual).isNull()
    }

    @Test
    fun getCurrentLocation_cancellation() {
        every { locationTask.isComplete } returns false
        val canceledSlot = slot<OnCanceledListener>()
        every { locationTask.addOnCanceledListener(capture(canceledSlot)) } answers {
            canceledSlot.captured.onCanceled()
            locationTask
        }

        val block: () -> Unit = { runBlocking { sut.getCurrentLocation() } }

        assertThrows<CancellationException>(block)
    }

    @Test
    fun getCurrentLocation_noAccessCoarseLocationPermission_returnsNull() = runTest {
        every {
            app.checkPermission(ACCESS_COARSE_LOCATION, any(), any())
        } returns PERMISSION_DENIED

        val actual = sut.getCurrentLocation()

        assertThat(actual).isNull()
    }

    @Test
    fun getCurrentLocation_noAccessFineLocationPermission_returnsNull() = runTest {
        every {
            app.checkPermission(ACCESS_FINE_LOCATION, any(), any())
        } returns PERMISSION_DENIED

        val actual = sut.getCurrentLocation()

        assertThat(actual).isNull()
    }

    @Test
    fun getCurrentLocation_noLocationProviderEnabled_returnsNull() = runTest {
        every { locationManager.isProviderEnabled(any()) } returns false

        val actual = sut.getCurrentLocation()

        assertThat(actual).isNull()
    }
}
