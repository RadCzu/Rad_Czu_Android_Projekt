package com.example.ad_gdynia.googlemaps

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ad_gdynia.database.entity.Location
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

//Google-maps compose component that shows a map with a location marker
@Composable
fun MapScreen(
    location: Location,
) {
    var myposition = LatLng(location.latitude.toDouble(), location.longitude.toDouble())
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(myposition, 15F, 0.0F, 0.0F)

    }

    val mapProperties = MapProperties(
        isMyLocationEnabled = true,
    )

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
    ) {
        Marker(
            state = MarkerState(position = myposition),
            title = location.name,
        )
    }
}