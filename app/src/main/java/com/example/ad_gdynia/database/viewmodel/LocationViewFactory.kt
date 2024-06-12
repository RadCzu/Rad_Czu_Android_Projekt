package com.example.ad_gdynia.database.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ad_gdynia.database.dao.LocationDao
import com.example.ad_gdynia.database.repository.LocationRepository

class LocationViewModelFactory(
    private val repository: LocationRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationViewModel::class.java)) {
            return LocationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}