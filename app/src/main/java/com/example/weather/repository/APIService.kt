package com.example.weather.repository

import com.example.weather.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface APIService {

    @GET("weather")
    fun getListOfNews(@Query("lat") lat : Double, @Query("lon") lon : Double,
                      @Query("appid") appID : String): Call<WeatherResponse>

}