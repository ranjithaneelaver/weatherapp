package com.weatherapp


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.weatherapp.model.WeatherInfoResponse
import com.weatherapp.wethermodel.WeatherForecastData
import okhttp3.Headers

class Repository {


    fun getWhetherResponse(): LiveData<Resource<WeatherForecastData>> {
        val data = MutableLiveData<Resource<WeatherForecastData>>()

      //  val call = ApiInterface().getWetherValues("bangalore", "177a607f441dae2fbc776cabc17e2454")

        val call = ApiInterface().getWetherValues("12.9716","77.5946", "177a607f441dae2fbc776cabc17e2454","metric")
        call.enqueue(object : ResponseListener<WeatherForecastData>() {
            override fun onResponse(t: WeatherForecastData?, headers: Headers) {
                data.setValue(Resource.Success(t!!))
            }


            override fun onError(message: String) {
                data.setValue(Resource.Error("error"))

            }
        })
        return data
    }

    fun getWhetherTodayResponse(): LiveData<Resource<WeatherInfoResponse>> {
        val data = MutableLiveData<Resource<WeatherInfoResponse>>()

        //  val call = ApiInterface().getWetherValues("bangalore", "177a607f441dae2fbc776cabc17e2454")

        val call = ApiInterface().getWetherForeCastValues("bangalore", "177a607f441dae2fbc776cabc17e2454")
        call.enqueue(object : ResponseListener<WeatherInfoResponse>() {
            override fun onResponse(t: WeatherInfoResponse?, headers: Headers) {
                data.setValue(Resource.Success(t!!))
            }


            override fun onError(message: String) {
                data.setValue(Resource.Error("error"))

            }
        })
        return data
    }

}