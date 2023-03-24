package com.example.weatherapp.presentasion.detailweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityDetailWeatherBinding
import com.example.weatherapp.model.response.WeatherResponse
import com.example.weatherapp.presentasion.vmfactory.ViewModelFactory
import java.lang.Math.round

class DetailWeatherActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailWeatherBinding
    private val viewModel by viewModels<DetailWeatherViewModel> {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailWeatherBinding.inflate(layoutInflater)

        val weatherDetail = intent.getParcelableExtra<WeatherResponse>(idDetail)
        Log.d("detail_activity",weatherDetail.toString())
        if (weatherDetail != null) {
            showDetail(weatherDetail)
            binding.tvDetailtoolbar.title = weatherDetail.name
            showForecast(weatherDetail.name)
            this.setSupportActionBar(binding.tvDetailtoolbar)
        }

        setContentView(binding.root)
    }

    private fun showDetail(data : WeatherResponse){
        val url = data.weather[0].icon
        val icon = "http://openweathermap.org/img/w/${url}.png"

        val temp = round(data.main.temp).toInt()

        binding.apply {
            tvCondition.text = data.weather[0].main
            Glide.with(this@DetailWeatherActivity)
                .load(icon)
                .into(binding.imageView)
            tvTemperature.text = getString(R.string.temperature, temp.toString())

            tvHumidty.text = getString(R.string.humidty, data.main.humidity.toString())
            tvWind.text = getString(R.string.windspeed, data.wind.speed.toString())
            tvVisibilty.text = getString(R.string.visibilty, data.visibility.toString())
        }
    }

    private fun showForecast(location : String){
        viewModel.getForecastData(location)
        viewModel.forecastData.observe(this){ data ->
            val adapter = ForecastAdapter(data.list)
            val recview = binding.rvForecast
            recview.adapter = adapter
            recview.layoutManager = LinearLayoutManager(this)

        }
    }

    companion object{
        const val idDetail = "detailActivity"
    }
}