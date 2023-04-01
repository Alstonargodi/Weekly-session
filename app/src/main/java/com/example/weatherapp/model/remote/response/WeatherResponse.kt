package com.example.weatherapp.model.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class WeatherResponse(
    @SerializedName("base")
    var base: String,
    @SerializedName("clouds")
    var clouds: Clouds,
    @SerializedName("cod")
    var cod: Int,
    @SerializedName("dt")
    var dt: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("main")
    var main: Main,
    @SerializedName("name")
    var name: String,
    @SerializedName("sys")
    var sys: Sys,
    @SerializedName("timezone")
    var timezone: Int,
    @SerializedName("visibility")
    var visibility: Int,
    @SerializedName("weather")
    var weather: List<WeatherX>,
    @SerializedName("wind")
    var wind: Wind
): Parcelable

@Parcelize
class Clouds(
    @SerializedName("all")
    var all: Int
): Parcelable

@Parcelize
class Sys(
    @SerializedName("country")
    var country: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("sunrise")
    var sunrise: Int,
    @SerializedName("sunset")
    var sunset: Int,
    @SerializedName("type")
    var type: Int
):Parcelable

@Parcelize
class Main(
    @SerializedName("feels_like")
    var feelsLike: Double,
    @SerializedName("humidity")
    var humidity: Int,
    @SerializedName("pressure")
    var pressure: Int,
    @SerializedName("temp")
    var temp: Double,
    @SerializedName("temp_max")
    var tempMax: Double,
    @SerializedName("temp_min")
    var tempMin: Double
): Parcelable

@Parcelize
class WeatherX(
    @SerializedName("description")
    var description: String,
    @SerializedName("icon")
    var icon: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("main")
    var main: String
): Parcelable

@Parcelize
class Wind(
    @SerializedName("deg")
    var deg: Int,
    @SerializedName("speed")
    var speed: Double
):Parcelable