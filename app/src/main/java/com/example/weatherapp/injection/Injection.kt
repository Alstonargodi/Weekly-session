package com.example.weatherapp.injection

import android.content.Context
import com.example.weatherapp.repository.Repository
import com.example.weatherapp.model.remote.apiconfig.ApiConfig

object Injection {
    fun provideRemoteRespoitory(context: Context): Repository {
        return Repository(
            ApiConfig.apiConfig(),
            context
        )
    }
}