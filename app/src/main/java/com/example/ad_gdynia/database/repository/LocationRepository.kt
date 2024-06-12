package com.example.ad_gdynia.database.repository

import androidx.annotation.WorkerThread
import com.example.ad_gdynia.database.dao.LocationDao
import com.example.ad_gdynia.database.entity.Location
import kotlinx.coroutines.flow.Flow

// Repository for the location, allows getting all locations via Flow
class LocationRepository(private val locationDao: LocationDao) {
    val allLocations: Flow<List<Location>> = locationDao.getLocationsOrderedByName()

    @WorkerThread
    suspend fun upsert(location: Location) {
        locationDao.upsertLocation(location)
    }

    @WorkerThread
    suspend fun delete(location: Location) {
        locationDao.deleteLocation(location)
    }
}