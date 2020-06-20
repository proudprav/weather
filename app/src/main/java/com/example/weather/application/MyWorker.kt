package com.example.weather.application

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.weather.di.APIModule
import com.example.weather.di.DaggerAPIComponent
import com.example.weather.repository.ApiURL
import com.example.weather.repository.RetrofitRepository
import com.example.weather.utils.Utility
import com.google.android.gms.location.LocationServices
import javax.inject.Inject

class MyWorker(private val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    @Inject
    lateinit var retrofitRepository: RetrofitRepository

    override fun doWork(): Result {
        DaggerAPIComponent.builder().aPIModule(APIModule(ApiURL.BASE_URL))
            .build().inject(this)
            requestLocationAndGetWeather()
        return Result.success()
    }

    private fun requestLocationAndGetWeather(){
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            LocationServices.getFusedLocationProviderClient(WeatherApplication.ctx!!)
                ?.lastLocation
                ?.addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        retrofitRepository.fetchWeatherForTheLocation(location.latitude, location.longitude, Utility.APP_ID)
                    }
                }
        }
    }
}