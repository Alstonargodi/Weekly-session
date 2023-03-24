package com.example.weatherapp.injection

import android.content.Context
import com.example.weatherapp.model.Repository
import com.example.weatherapp.model.apiconfig.ApiConfig

object Injection {
    fun provideRemoteRespoitory(): Repository{
        return Repository(ApiConfig.apiConfig())
    }
}