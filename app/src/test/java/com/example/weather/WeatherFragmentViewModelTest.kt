package com.example.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.weather.model.WeatherResponse
import com.example.weather.repository.RetrofitRepository
import com.example.weather.ui.viewmodel.WeatherFragmentViewModel
import com.google.gson.Gson
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherFragmentViewModelTest {

    @Mock
    lateinit var retrofitRepository: RetrofitRepository

    @Mock
    lateinit var viewModel: WeatherFragmentViewModel
    private val mockLiveData = MutableLiveData<WeatherResponse>()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = WeatherFragmentViewModel(
            retrofitRepository
        )
        mockData()
    }

    @Test
    fun getListOfArticleNullTest() {
        Mockito.`when`(retrofitRepository.getWeather()).thenReturn(mockLiveData)
        Assert.assertNotNull(viewModel.getWeather()?.value)
        Assert.assertNotNull(viewModel.getWeather()?.hasActiveObservers())
    }

    private fun mockData() {
        val gson = Gson()
        var weatherResponse = gson.fromJson(ghgh, WeatherResponse::class.java)
        mockLiveData.postValue(weatherResponse)

    }

    val ghgh =
        "{\"base\":\"stations\",\"clouds\":{\"all\":40},\"cod\":200,\"coord\":{\"lat\":37.42," +
                "\"lon\":-122.08},\"dt\":1592624317,\"id\":5375480,\"main\":{\"feels_like\":288.93,\"humid" +
                "ity\":60,\"pressure\":1012,\"temp\":292.16,\"temp_max\":293.71,\"temp_min\":290.93},\"na" +
                "me\":\"Mountain View\",\"sys\":{\"country\":\"US\",\"id\":5845,\"sunrise\":1592570849" +
                ",\"sunset\":1592623928,\"type\":1},\"timezone\":-25200,\"visibility\":16093,\"weath" +
                "er\":[{\"description\":\"scattered clouds\",\"icon\":\"03n\",\"id\":802,\"ma" +
                "in\":\"Clouds\"}],\"wind\":{\"deg\":310,\"speed\":5.1}}"

}