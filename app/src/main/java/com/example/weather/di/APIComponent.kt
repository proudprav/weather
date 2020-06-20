package com.example.weather.di

import com.example.weather.application.AppModule
import com.example.weather.application.MyWorker
import com.example.weather.repository.RetrofitRepository
import com.example.weather.utils.ViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, APIModule::class])
interface APIComponent {

    fun inject(retrofitRepository: RetrofitRepository)

    fun inject(retrofitRepository: ViewModelFactory)

    fun inject(myWorker: MyWorker)



}