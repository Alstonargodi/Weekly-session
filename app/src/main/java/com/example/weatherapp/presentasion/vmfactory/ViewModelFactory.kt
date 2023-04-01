package com.example.weatherapp.presentasion.vmfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.injection.Injection
import com.example.weatherapp.repository.Repository
import com.example.weatherapp.presentasion.detailweather.viewmodel.DetailWeatherViewModel
import com.example.weatherapp.presentasion.home.MainActivityViewModel

class ViewModelFactory private constructor(
    private val repository: Repository
): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainActivityViewModel::class.java)->{
                MainActivityViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailWeatherViewModel::class.java)->{
                DetailWeatherViewModel(repository) as T
            }
            else -> throw IllegalArgumentException(
                "Unknown ViewModel class: " + modelClass.name
            )
        }
    }

    companion object{
        @Volatile
        private var instance : ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(
                    Injection.provideRemoteRespoitory(context)
                )
            }.also { instance = it }
    }
}