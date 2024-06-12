package com.example.ad_gdynia.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.ad_gdynia.database.entity.Location
import kotlinx.coroutines.flow.Flow

// Wrote a DAO (Data Access Object) for the room database, didnt have time to implement adding new locations via the app
@Dao
interface LocationDao {

    @Upsert
    suspend fun upsertLocation(location: Location)

    @Delete
    suspend fun deleteLocation(location: Location)

    @Query("SELECT * FROM locations ORDER BY name")
    fun getLocationsOrderedByName(): Flow<List<Location>>
}
