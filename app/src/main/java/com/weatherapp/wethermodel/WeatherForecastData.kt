package com.weatherapp.wethermodel

data class WeatherForecastData(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<X>,
    val message: Int
)