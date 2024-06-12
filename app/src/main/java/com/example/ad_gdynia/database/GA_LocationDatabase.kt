package com.example.ad_gdynia.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.ad_gdynia.database.dao.LocationDao
import com.example.ad_gdynia.database.entity.Location

// Class that establishes the database connection
@Database(entities = [Location::class], version = 1, exportSchema = false)
abstract class GA_LocationDatabase : RoomDatabase() {
    abstract val dao: LocationDao

    companion object {
        private const val TAG = "DB"

        @Volatile
        private var INSTANCE: GA_LocationDatabase? = null

        fun getInstance(context: Context): GA_LocationDatabase {
            Log.d(TAG, "Attempting database connection")
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GA_LocationDatabase::class.java,
                    "GA_locations.db"
                )
                    .createFromAsset("GA_locations.db")
                    .build()

                INSTANCE = instance
                instance
            }
        }

    }
}