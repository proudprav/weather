package com.example.weather.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weather.R
import com.example.weather.model.WeatherResponse
import com.example.weather.ui.viewmodel.WeatherFragmentViewModel
import com.example.weather.utils.Utility.Companion.convertToCelsius
import com.example.weather.utils.Utility.Companion.convertToDate
import com.example.weather.utils.ViewModelFactory
import kotlinx.android.synthetic.main.weather_fragment.*

class WeatherFragment : Fragment() {
    private lateinit var viewModel: WeatherFragmentViewModel

    companion object {
        fun newInstance() = WeatherFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this,viewModelFactory).
        get(WeatherFragmentViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getWeather()?.observe(viewLifecycleOwner,
            Observer { renderUI(it) }
        )

    }

    private fun renderUI(weatherResponse: WeatherResponse?) {
        weatherResponse?.let {
           tv_city_name.text =  it.name
            tv_temp.text = it.main.temp.convertToCelsius(it.main.feels_like)
            tv_sun_rise.text = it.sys.sunrise.convertToDate()
            tv_sun_set.text = it.sys.sunset.convertToDate()
        }
    }

}