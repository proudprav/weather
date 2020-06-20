package com.example.weather.application

import android.app.Application
import android.content.Context
import com.example.weather.di.APIComponent
import com.example.weather.di.APIModule
import com.example.weather.di.DaggerAPIComponent
import com.example.weather.repository.ApiURL
import com.example.weather.utils.Utility


class WeatherApplication : Application() {

    companion object {
        var ctx: Context? = null
        lateinit var apiComponent: APIComponent

    }

    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
        apiComponent = initDaggerComponent()
    }

    private fun initDaggerComponent(): APIComponent {
        apiComponent = DaggerAPIComponent
            .builder()
            .aPIModule(APIModule(ApiURL.BASE_URL))
            .build()
        return apiComponent

    }
}