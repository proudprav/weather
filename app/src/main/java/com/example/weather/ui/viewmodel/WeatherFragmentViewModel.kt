package com.example.weather.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weather.repository.RetrofitRepository

class WeatherFragmentViewModel(private val retrofitRepository: RetrofitRepository) : ViewModel() {


    fun getWeather()=
        retrofitRepository.getWeather()


}