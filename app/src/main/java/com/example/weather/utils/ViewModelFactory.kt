package com.example.weather.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.application.WeatherApplication
import com.example.weather.di.APIComponent
import com.example.weather.repository.RetrofitRepository
import com.example.weather.ui.viewmodel.WeatherFragmentViewModel

import javax.inject.Inject

class ViewModelFactory : ViewModelProvider.Factory {
    @Inject
    lateinit var retrofitRepository: RetrofitRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val apiComponent: APIComponent = WeatherApplication.apiComponent
        apiComponent.inject(this)
        if (modelClass.isAssignableFrom(WeatherFragmentViewModel::class.java)) {
            return WeatherFragmentViewModel(retrofitRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
