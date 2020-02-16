package com.weatherapp.model

import com.google.gson.annotations.SerializedName

data class Main(


    @SerializedName("temp")
val temp: Double = 0.0,
@SerializedName("pressure")
val pressure: Double = 0.0,
@SerializedName("humidity")
val humidity: Int = 0,
@SerializedName("temp_min")
val tempMin: Double = 0.0,
@SerializedName("temp_max")
val tempMax: Double = 0.0


 /*   val feels_like: Double,
    val grnd_level: Int,
    val humidity: Int,
    val pressure: Int,
    val sea_level: Int,
    val temp: Double,
    val temp_kf: Int,
    val temp_max: Double,
    val temp_min: Double*/
)