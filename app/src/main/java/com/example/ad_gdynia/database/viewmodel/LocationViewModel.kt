package com.example.ad_gdynia.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ad_gdynia.database.dao.LocationDao
import com.example.ad_gdynia.database.entity.Location
import com.example.ad_gdynia.database.repository.LocationRepository
import kotlinx.coroutines.launch

class LocationViewModel(
    private val repository: LocationRepository
) : ViewModel() {

    val locations = repository.allLocations.asLiveData()

    fun upsertLocation(location: Location) {
        viewModelScope.launch {
            repository.upsert(location)
        }
    }

    fun deleteLocation(location: Location) {
        viewModelScope.launch {
            repository.delete(location)
        }
    }
}
