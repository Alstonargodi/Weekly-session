package com.example.weatherapp.presentasion.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.Repository
import com.example.weatherapp.model.response.WeatherResponse
import com.example.weatherapp.model.utils.ResultRespon

class MainActivityViewModel(
    private val repository: Repository
): ViewModel() {
    suspend fun getWeatherData(location : String): LiveData<ResultRespon<WeatherResponse>> =
        repository.getWeatherDetail(location)
}