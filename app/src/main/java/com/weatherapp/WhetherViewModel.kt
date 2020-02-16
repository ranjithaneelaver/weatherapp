package com.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.weatherapp.model.WeatherInfoResponse
import com.weatherapp.wethermodel.WeatherForecastData


class WhetherViewModel() : ViewModel() {
    private val repository: Repository = Repository()
    private lateinit var  whetherResponse : LiveData<Resource<WeatherForecastData>>

    private lateinit var  todayResponse : LiveData<Resource<WeatherInfoResponse>>

    fun getResponse(): LiveData<Resource<WeatherForecastData>> {
         whetherResponse = repository.getWhetherResponse()

        return  whetherResponse
    }

    fun getTodayWeatherResponse():LiveData<Resource<WeatherInfoResponse>>{
        todayResponse = repository.getWhetherTodayResponse()
        return  todayResponse

    }

}