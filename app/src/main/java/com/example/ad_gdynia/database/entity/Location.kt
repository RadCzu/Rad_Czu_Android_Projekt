package com.example.ad_gdynia.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob

@Entity(tableName = "locations")
data class Location(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "longitude") val longitude: Float,
    @ColumnInfo(name = "latitude") val latitude: Float,
    @ColumnInfo(name = "image") val image: ByteArray,
)