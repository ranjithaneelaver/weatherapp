package com.weatherapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.weatherapp.databinding.LayoutWeatherAppBinding
import com.weatherapp.model.WeatherData
import com.weatherapp.wethermodel.WeatherForecastData
import kelvinToCelsius
import unixTimestampToDateTimeString


class ForecastAdapter(val context: Context,val weatherlist: WeatherForecastData
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecyclerViewHolder(
        DataBindingUtil.inflate<LayoutWeatherAppBinding>(
            LayoutInflater.from(parent.context),
            R.layout.layout_weather_app,
            parent,
            false
        )
    )

    override fun getItemCount() = weatherlist.list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding: LayoutWeatherAppBinding =
            (holder as RecyclerViewHolder<*>).binding as LayoutWeatherAppBinding

        val weatherData = weatherlist.list.get(position).dt?.unixTimestampToDateTimeString()?.let { it1 ->
            WeatherData(
                dateTime = it1,
                temperature = weatherlist.list.get(position).main?.temp.kelvinToCelsius().toString(),
                cityAndCountry = "Bengaloru,IN",
                weatherConditionIconUrl = "http://openweathermap.org/img/w/${weatherlist.list.get(position).weather[0].icon}.png",
                weatherConditionIconDescription = weatherlist.list.get(position)?.weather[0].description,
                humidity = "${weatherlist.list.get(position).main.humidity}%",
                pressure = "${weatherlist.list.get(position).main.pressure} mBar"
              //  visibility = "${weatherlist.list.get(position).visibility/1000.0} KM",
                //sunrise = weatherlist.list.get(position).sys.sunrise.unixTimestampToTimeString(),
                //sunset = it.data?.sys.sunset.unixTimestampToTimeString()
            )
        }

        binding.viewmodel = weatherData

        Glide.with(context).load(weatherData.weatherConditionIconUrl).into(binding.layoutWeatherBasic.ivWeatherCondition)




        binding.executePendingBindings()
    }

}