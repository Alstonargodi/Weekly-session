package com.example.weatherapp.model.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteWeather(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id: Int = 0,
    @ColumnInfo("location")
    var location : String = "",
    @ColumnInfo("isFavorite")
    var isFavorite : Boolean = false,
    @ColumnInfo("dateAdd")
    var dateAdd : String = "",
    @ColumnInfo("description")
    var description : String = "",
    @ColumnInfo("temperature")
    var temperature : Int = 0,
    @ColumnInfo("iconUrl")
    var iconUrl : String = "",
)