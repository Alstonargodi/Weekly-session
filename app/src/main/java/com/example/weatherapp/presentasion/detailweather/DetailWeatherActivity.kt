package com.example.weatherapp.presentasion.detailweather

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityDetailWeatherBinding
import com.example.weatherapp.model.local.entities.FavoriteWeather
import com.example.weatherapp.model.remote.response.WeatherResponse
import com.example.weatherapp.presentasion.detailweather.adapter.ForecastAdapter
import com.example.weatherapp.presentasion.detailweather.viewmodel.DetailWeatherViewModel
import com.example.weatherapp.presentasion.home.MainActivity
import com.example.weatherapp.presentasion.vmfactory.ViewModelFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class DetailWeatherActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailWeatherBinding
    private lateinit var weatherDetail : WeatherResponse

    private val viewModel by viewModels<DetailWeatherViewModel> {
        ViewModelFactory.getInstance(this)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailWeatherBinding.inflate(layoutInflater)

        weatherDetail = intent.getParcelableExtra(idDetail)!!
        showDetail(weatherDetail)
        showForecast(weatherDetail.name)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        binding.tvDetailtoolbar.apply {
            title = weatherDetail.name
            navigationIcon = resources.getDrawable(R.drawable.baseline_arrow_back_24)
            setNavigationOnClickListener {
                startActivity(
                    Intent(
                        this@DetailWeatherActivity,
                        MainActivity::class.java
                    )
                )
            }
        }
        setSupportActionBar(binding.tvDetailtoolbar)
        setContentView(binding.root)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail,menu)

        val favorite = menu?.findItem(R.id.favoriteDetail)

        favorite?.setOnMenuItemClickListener {
            setAsFavorite(weatherDetail)
            true
        }

        return super.onCreateOptionsMenu(menu)
    }

    private fun showDetail(data : WeatherResponse){
        val url = data.weather[0].icon
        val icon = "http://openweathermap.org/img/w/${url}.png"

        val temp = data.main.temp.roundToInt()

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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setAsFavorite(data : WeatherResponse){
        Toast.makeText(
            this,
            getString(R.string.set_as_favorite, data.name),
            Toast.LENGTH_LONG
        ).show()

        val temp  = data.main.temp.roundToInt()
        val dateFormat = DateTimeFormatter.ofPattern(
            "yyy-MM-dd HH:mm"
        )
        val url = data.weather[0].icon
        val icon = "http://openweathermap.org/img/w/${url}.png"
        val current = LocalDateTime.now().format(dateFormat)

        viewModel.saveFavoriteWeather(
            FavoriteWeather(
                id = 0,
                location = weatherDetail.name,
                isFavorite = true,
                dateAdd = current,
                description = weatherDetail.weather[0].description,
                temperature = temp,
                iconUrl = icon
            )
        )
    }
    companion object{
        const val idDetail = "detailActivity"
    }
}