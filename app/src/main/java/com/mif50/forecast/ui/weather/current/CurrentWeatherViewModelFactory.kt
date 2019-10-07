package com.mif50.forecast.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mif50.forecast.data.provider.UnitProvider
import com.mif50.forecast.data.repository.ForecastRepository


class CurrentWeatherViewModelFactory(private val forecastRepository: ForecastRepository,
                                     private val unitProvider: UnitProvider) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentBaseViewModel(forecastRepository, unitProvider) as T
    }
}