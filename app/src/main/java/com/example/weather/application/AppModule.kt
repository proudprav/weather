package com.example.weather.application

import dagger.Module
import dagger.Provides

@Module
class AppModule constructor(private var myApplication: WeatherApplication) {

    @Provides
    fun provideMyRetroApplication(): WeatherApplication {
        return myApplication
    }
}