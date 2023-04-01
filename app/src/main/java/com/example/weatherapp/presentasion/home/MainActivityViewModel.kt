package com.example.weatherapp.presentasion.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.local.entities.FavoriteWeather
import com.example.weatherapp.repository.Repository
import com.example.weatherapp.model.remote.response.WeatherResponse
import com.example.weatherapp.model.remote.utils.ResultRespon

class MainActivityViewModel(
    private val repository: Repository
): ViewModel() {
    suspend fun getWeatherData(location : String): LiveData<ResultRespon<WeatherResponse>> =
        repository.getWeatherDetail(location)

    fun readFavorite(): LiveData<List<FavoriteWeather>> =
        repository.readFavorite()

    fun deleteFavorite(data : FavoriteWeather){
        repository.deleteFavorite(data)
    }
}