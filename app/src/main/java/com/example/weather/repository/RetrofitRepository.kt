package com.example.weather.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weather.application.WeatherApplication
import com.example.weather.di.APIComponent
import com.example.weather.model.WeatherResponse
import com.example.weather.utils.Utility
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class RetrofitRepository {
    private var apiComponent: APIComponent = WeatherApplication.apiComponent
    var live = MutableLiveData<WeatherResponse>()

    @Inject
    lateinit var retrofit: Retrofit
    val gson = Gson()

    init {
        apiComponent.inject(this)
    }

    fun fetchWeatherForTheLocation(lat: Double, long: Double, appid: String) {
        Log.d(this.javaClass.simpleName, ": fetching weather for the location")
        val apiService: APIService = retrofit.create(APIService::class.java)
        val listOfNews = apiService.getListOfNews(lat, long, appid)
        listOfNews.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>, response: Response<WeatherResponse>
            ) {
                response.body().let {
                    WeatherApplication.ctx?.let { it1 ->
                        Utility.create(
                            it1, "location.json",
                            gson.toJson(response.body(), WeatherResponse::class.java)
                        )
                        getWeather()
                    }
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.d("RetroRepository", "Failed:::" + t.message)
            }
        })
    }

    @SuppressLint("MissingPermission")
    fun getWeather(): LiveData<WeatherResponse>? {
        if (Utility.isFilePresent(WeatherApplication.ctx!!, "location.json")) {
            live.value = gson.fromJson(
                Utility.read(WeatherApplication.ctx!!, "location.json"),
                WeatherResponse::class.java
            )
            return live
        }
        return null
    }

}



