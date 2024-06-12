package com.example.ad_gdynia

import LocationItem
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.ad_gdynia.database.entity.Location
import kotlinx.coroutines.flow.Flow

// Lazy list of all locations
@Composable
    fun LocationList(locationsFlow: Flow<List<Location>>, onNavigate: (Location) -> Unit) {

        val locationsState by locationsFlow.collectAsState(initial = emptyList())
        Log.d("LocationList", "got some locations ${locationsState.size}" )
        LazyColumn {
            items(locationsState) { location ->
                LocationItem(location, onNavigate)
            }
        }
    }
