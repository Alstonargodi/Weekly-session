package com.example.weatherapp.presentasion.detailweather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.Repository
import com.example.weatherapp.model.apiconfig.ApiConfig
import com.example.weatherapp.model.apiservice.ApiService
import com.example.weatherapp.model.response.ForecastResponse
import com.example.weatherapp.model.response.WeatherResponse
import com.example.weatherapp.model.utils.ResultRespon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailWeatherViewModel(
    private val repository: Repository
): ViewModel() {
    private var _forecastData = MutableLiveData<ForecastResponse>()
    var forecastData : LiveData<ForecastResponse> = _forecastData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<String>()
    val isError : LiveData<String> = _isError


    fun getForecastData(location: String){
        _isLoading.value = true
        val service = ApiConfig.apiConfig().getForecast(location)
        service.enqueue(object : Callback<ForecastResponse>{
            override fun onResponse(
                call: Call<ForecastResponse>,
                response: Response<ForecastResponse>
            ) {
              _isLoading.value = false
                if(response.isSuccessful){
                    _forecastData.value = response.body()
                }else{
                    Log.d("viewmodel",response.message())
                }
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = t.message.toString()
            }
        })
    }

}