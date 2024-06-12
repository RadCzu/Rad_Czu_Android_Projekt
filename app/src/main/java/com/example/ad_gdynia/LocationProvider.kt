package com.example.ad_gdynia

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.ad_gdynia.dataclasses.Vector2
import com.google.android.gms.location.*
import kotlinx.coroutines.flow.MutableStateFlow

class LocationProvider(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private lateinit var locationCallback: LocationCallback

    // State flow of location is updated in real time
    val currentLocation = MutableStateFlow<Vector2?>(null)

    init {
        setupLocationCallback()
    }

    // Initialise connection to the phone GPS
    private fun setupLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    if (location != null) {
                        val newLocation = Vector2(location.latitude, location.longitude)
                        Log.d("LocationProvider", "New location: $newLocation")
                        currentLocation.value = newLocation
                    }
                }
            }
        }
    }


    fun startLocationUpdates() {
        // Check if the location permissions were granted
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("LocationProvider", "Location permission not granted")
            return
        }

        // If yes, start location requests every minute
        val locationRequest = LocationRequest.Builder(1000L)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        Log.d("LocationProvider", "Location updates started")
        // Request current location
        updateLocation()
    }

    // Request current location
    private fun updateLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("LocationProvider", "Location permission not granted")
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                currentLocation.value = Vector2(it.latitude, it.longitude)
                Log.d("LocationProvider", "Updated location: $currentLocation")
            } ?: run {
                Log.d("LocationProvider", "Last known location is null")
            }
        }.addOnFailureListener { exception ->
            Log.e("LocationProvider", "Failed to get last known location", exception)
        }
    }

    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        Log.d("LocationProvider", "Location updates stopped")
    }
}