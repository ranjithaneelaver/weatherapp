package com.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.weatherapp.databinding.ActivityMain2Binding
import com.weatherapp.model.WeatherData
import kelvinToCelsius
import kotlinx.android.synthetic.main.activity_main2.*
import unixTimestampToDateTimeString
import unixTimestampToTimeString

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    private var viewModel: WhetherViewModel = WhetherViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)
        viewModel = ViewModelProvider(this).get(WhetherViewModel::class.java)



        viewModel.getTodayWeatherResponse().observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    val weatherData = it.data?.dt?.unixTimestampToDateTimeString()?.let { it1 ->
                        WeatherData(
                            dateTime = it1,
                            temperature = it.data?.main?.temp.kelvinToCelsius().toString(),
                            cityAndCountry = "${it.data?.name}, ${it.data?.sys?.country}",
                            weatherConditionIconUrl = "http://openweathermap.org/img/w/${it.data!!.weather[0].icon}.png",
                            weatherConditionIconDescription = it.data?.weather[0].description,
                            humidity = "${it.data?.main.humidity}%",
                            pressure = "${it.data?.main.pressure} mBar",
                            visibility = "${it.data?.visibility / 1000.0} KM",
                            sunrise = it.data?.sys.sunrise.unixTimestampToTimeString(),
                            sunset = it.data?.sys.sunset.unixTimestampToTimeString()
                        )
                    }

                    setWeatherInfo(weatherData!!)
                }

                Resource.Status.ERROR -> {
                    Log.e("error", "error" + it.message)
                    binding.progressBar.visibility = View.GONE
                }

                Resource.Status.LOADING->{
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })


        viewModel.getResponse().observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerview.adapter = ForecastAdapter(this, it.data!!)

                }

                Resource.Status.ERROR -> {
                    Log.e("error", "error" + it.message)
                    binding.progressBar.visibility = View.GONE
                }

                Resource.Status.LOADING->{
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })


    }

    private fun setWeatherInfo(weatherData: WeatherData) {
        output_group.visibility = View.VISIBLE
        tv_error_message.visibility = View.GONE

        binding.viewmodel = weatherData
        Glide.with(this).load(weatherData.weatherConditionIconUrl).into(binding.layoutWeatherBasic.ivWeatherCondition)
    }
}
