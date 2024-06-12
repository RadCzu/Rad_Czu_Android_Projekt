package com.example.ad_gdynia


import LocationItem
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.example.ad_gdynia.database.entity.Location
import com.example.ad_gdynia.dataclasses.Vector2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.pow
import kotlin.math.sqrt

// Composable element that displays the closest location from all locations based on the locationProvider class
@Composable
fun ClosestLocationView(
    locationsFlow: Flow<List<Location>>,
    locationProvider: LocationProvider,
    onNavigate: (Location) -> Unit
) {
    val positionFlow = locationProvider.currentLocation

    val locationsState by locationsFlow.collectAsState(initial = emptyList())
    val positionState: Vector2? by positionFlow.collectAsState(initial = null)

    Log.d("ClosestLocationView", "locationsState size: ${locationsState.size}")
    Log.d("ClosestLocationView", "positionState: $positionState")

    val closestLocation = remember(locationsState, positionState) {
        calculateClosestLocation(locationsState, positionState)
    }

    closestLocation?.let { location ->
        Column {
            BasicText("Ciekawe miejsce blisko ciebie:")
            LocationItem(location, onNavigate)
        }
    } ?: BasicText("BRAK WYKRYTYCH MIEJSC")
}

private fun calculateClosestLocation(locations: List<Location>, position: Vector2?): Location? {
    if (locations.isNotEmpty() && position != null) {
        var bestDistance = Float.MAX_VALUE
        var bestLocation: Location? = null

        for (location in locations) {
            val distance = sqrt(
                (position.x - location.latitude).pow(2) +
                        (position.y - location.longitude).pow(2)
            ).toFloat()
            if (bestDistance > distance) {
                bestDistance = distance
                bestLocation = location
            }
        }
        return bestLocation
    }
    return null
}