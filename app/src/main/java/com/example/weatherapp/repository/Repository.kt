package com.example.weatherapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.weatherapp.model.local.database.FavoriteDatabase
import com.example.weatherapp.model.local.dao.FavoriteDao
import com.example.weatherapp.model.local.entities.FavoriteWeather
import com.example.weatherapp.model.remote.apiservice.ApiService
import com.example.weatherapp.model.remote.response.WeatherResponse
import com.example.weatherapp.model.remote.utils.ResultRespon
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository(
    private val apiService: ApiService,
    context: Context
){
    private var weatherDao : FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDatabase.getDatabase(context)
        weatherDao = db.favoriteDao()
    }

    suspend fun getWeatherDetail(location : String): LiveData<ResultRespon<WeatherResponse>> =
    liveData {
        emit(ResultRespon.Loading)
        try {
            val client = apiService.getWeatherData(location)
            emit(ResultRespon.Sucess(client))
        }catch (e : Exception){
            emit(ResultRespon.Error(e.message.toString()))
        }
    }

    fun readFavorite(): LiveData<List<FavoriteWeather>> =
        weatherDao.readFavorite()

    fun insertFavorite(data :FavoriteWeather){
        executorService.execute {
            weatherDao.insertFavorite(data)
        }
    }

    fun deleteFavorite(data : FavoriteWeather){
        executorService.execute {
            weatherDao.deleteFavorite(data)
        }
    }

}