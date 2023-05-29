package com.androidgeek.weather.feature.weather.data.repository

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.location.LocationManager.NETWORK_PROVIDER
import androidx.core.content.ContextCompat.checkSelfPermission
import com.androidgeek.weather.feature.weather.domain.location.model.Location
import com.androidgeek.weather.feature.weather.domain.location.repository.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationRepositoryImpl @Inject constructor(
        private val locationClient: FusedLocationProviderClient,
        private val app: Application
) : LocationRepository {

    override suspend fun getCurrentLocation(): Location? {
        val hasAccessFineLocationPermission =
            checkSelfPermission(app, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED

        val hasAccessCoarseLocationPermission =
            checkSelfPermission(app, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED

        val locationManager = app.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isLocationProviderEnabled = locationManager.isProviderEnabled(GPS_PROVIDER) ||
            locationManager.isProviderEnabled(NETWORK_PROVIDER)

        if (!hasAccessCoarseLocationPermission || !hasAccessFineLocationPermission || !isLocationProviderEnabled) {
            return null
        }

        return suspendCancellableCoroutine { continuation ->
            locationClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        continuation.resume(result.toDomain())
                    } else {
                        continuation.resume(null)
                    }
                    return@suspendCancellableCoroutine
                }

                addOnSuccessListener {
                    continuation.resume(it.toDomain())
                }

                addOnFailureListener {
                    continuation.resume(null)
                }

                addOnCanceledListener {
                    continuation.cancel()
                }
            }
        }
    }

}

fun android.location.Location.toDomain(): Location =
    Location(
        lat = this.latitude,
        long = this.longitude
    )
