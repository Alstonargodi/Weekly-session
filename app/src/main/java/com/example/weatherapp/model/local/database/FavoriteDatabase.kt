package com.example.weatherapp.model.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.model.local.dao.FavoriteDao
import com.example.weatherapp.model.local.entities.FavoriteWeather

@Database(
    entities = [FavoriteWeather::class],
    version = 1
)
abstract class FavoriteDatabase : RoomDatabase(){
    abstract fun favoriteDao(): FavoriteDao

    companion object{
        @Volatile
        private var INSTANCE : FavoriteDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteDatabase::class.java, "favorite_db")
                        .build()
                }
            }
            return INSTANCE as FavoriteDatabase
        }
    }
}