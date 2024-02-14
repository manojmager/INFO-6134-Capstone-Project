package com.newsportal.info_6134capstoneproject

import com.google.android.gms.location.FusedLocationProviderClient

class LocationProvider(private val fusedLocationClient: FusedLocationProviderClient) {

    interface LocationFetchListener {
        fun onLocationFetched(latitude: Double, longitude: Double)
        fun onLocationFetchFailed(error: String)
    }

    fun fetchUserLocation(listener: LocationFetchListener) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    listener.onLocationFetched(latitude, longitude)
                } else {
                    listener.onLocationFetchFailed("Location is null")
                }
            }
            .addOnFailureListener { e ->
                listener.onLocationFetchFailed(e.message ?: "Unknown error")
            }
    }
}
