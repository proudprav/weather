package com.example.weather.di


import com.example.weather.repository.RetrofitRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class APIModule constructor(private val baseURL: String) {

    @Singleton
    @Provides
    fun provideOKHttpClient(): OkHttpClient =
        OkHttpClient.Builder().readTimeout(1200, TimeUnit.SECONDS)
            .connectTimeout(1200, TimeUnit.SECONDS).build()

    @Singleton
    @Provides
    fun provideGSON(): GsonConverterFactory = GsonConverterFactory.create(GsonBuilder().create())

    @Singleton
    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseURL).addConverterFactory(gsonConverterFactory).client(okHttpClient).build()

    @Provides
    fun provideRetroRepository() = RetrofitRepository()

}