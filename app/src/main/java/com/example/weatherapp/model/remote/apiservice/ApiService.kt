package com.example.weatherapp.model.remote.apiservice

import com.example.weatherapp.model.remote.response.ForecastResponse
import com.example.weatherapp.model.remote.response.WeatherResponse
import com.example.weatherapp.model.remote.utils.Utils
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
        @GET("weather")
     suspend fun getWeatherData(
            @Query("q") Location : String,
            @Query("units") units : String = Utils.metric,
            @Query("appid") api_key : String = "adede588548f908529494b8da290c4e9"
    ): WeatherResponse

    @GET("forecast")
    fun getForecast(
        @Query("q") Location: String,
        @Query("units") units : String = Utils.metric,
        @Query("appid") apikey : String = "adede588548f908529494b8da290c4e9"
    ): Call<ForecastResponse>

}