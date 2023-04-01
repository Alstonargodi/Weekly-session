package com.example.weatherapp.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weatherapp.model.local.entities.FavoriteWeather

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(data : FavoriteWeather)

    @Query("select * from FavoriteWeather order by id asc")
    fun readFavorite(): LiveData<List<FavoriteWeather>>

    @Update
    fun updateFavorite(data : FavoriteWeather)

    @Delete
    fun deleteFavorite(data : FavoriteWeather)
}