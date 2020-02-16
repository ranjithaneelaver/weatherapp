package com.weatherapp

import com.weatherapp.model.WeatherInfoResponse
import com.weatherapp.model.WhetherResponse
import com.weatherapp.wethermodel.WeatherForecastData
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface  ApiInterface {


    @GET("forecast")
    fun getWetherValues(@Query("lat")  lat:String,@Query("lon")  lon:String,@Query("appid") apikey: String,@Query("unit") units :String): Call<WeatherForecastData>



    @GET("weather")
    fun getWetherForeCastValues(@Query("q")  name:String,@Query("appid") apikey: String): Call<WeatherInfoResponse>




    @GET("employees")
    fun getEmp():Call<WhetherResponse>

    companion object {
        operator fun invoke(
        ): ApiInterface {

            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "")
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }


            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
            }

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(requestInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        }
    }

}