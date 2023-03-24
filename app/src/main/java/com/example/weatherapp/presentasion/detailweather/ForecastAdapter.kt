package com.example.weatherapp.presentasion.detailweather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.ItemForecastBinding
import com.example.weatherapp.model.response.forecastItem

class ForecastAdapter(
    private val listData : List<forecastItem>
): RecyclerView.Adapter<ForecastAdapter.ViewHolder>(){

    class ViewHolder(val binding : ItemForecastBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemForecastBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        ))
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listData[position]

        val url = item.weather[0].icon
        val icon = "http://openweathermap.org/img/w/${url}.png"

        holder.apply {
            binding.tvTempForecast.text = item.main.temp.toString()
            Glide.with(holder.itemView.context)
                .load(icon)
                .into(holder.binding.imgIconForecast)
        }
    }
}