package com.example.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.example.weather.application.MyWorker
import com.example.weather.ui.view.WeatherFragment
import com.example.weather.utils.Utility
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherFragment.newInstance())
                .commitNow()

        }
        Utility.checkForLocationPermission(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            createWorkerForFetchingWeather()
        }
    }

    private fun createWorkerForFetchingWeather() {
        val constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.UNMETERED).build()
        val periodicWorkRequest =
            PeriodicWorkRequest.Builder(MyWorker::class.java, 15, TimeUnit.MINUTES)
                .setConstraints(constraints).build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            "location",
            ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest
        )
    }
}