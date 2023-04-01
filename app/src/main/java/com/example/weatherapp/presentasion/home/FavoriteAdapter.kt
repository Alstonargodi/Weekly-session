package com.example.weatherapp.presentasion.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.ItemWeatherBinding
import com.example.weatherapp.model.local.entities.FavoriteWeather

class FavoriteAdapter(
    private val listData : List<FavoriteWeather>
): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    class ViewHolder(val binding : ItemWeatherBinding)
        : RecyclerView.ViewHolder(binding.root){
        lateinit var dataFav : FavoriteWeather
        fun getData(): FavoriteWeather = dataFav
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : ViewHolder {
        return ViewHolder(ItemWeatherBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listData[position]
        holder.dataFav = item
        holder.binding.apply {
            tvFavLocation.text = item.location
            tvFavDescription.text = item.description
            tvFavTemp.text = item.temperature.toString()
        }
        Glide.with(holder.itemView.context)
            .load(item.iconUrl)
            .into(holder.binding.imgAvIcon)
    }

    override fun getItemCount(): Int = listData.size
}