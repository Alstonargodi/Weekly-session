package com.example.weatherapp.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.weatherapp.model.apiservice.ApiService
import com.example.weatherapp.model.response.WeatherResponse
import com.example.weatherapp.model.utils.ResultRespon
import java.lang.Exception

class Repository(
    private val apiService: ApiService
){
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
}