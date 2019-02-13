package com.mif50.forecast.ui.weather.current

import androidx.lifecycle.ViewModel
import com.mif50.forecast.data.provider.UnitProvider
import com.mif50.forecast.data.repository.ForecastRepository
import com.mif50.forecast.internal.UnitSystem
import com.mif50.forecast.internal.lazyDeferred

class CurrentWeatherViewModel(
        private val forecastRepository: ForecastRepository,
        unitProvider: UnitProvider) : ViewModel() {


    private val unitSystem = unitProvider.getUnitSystem() // get later from setting
    val isMetric = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred { forecastRepository.getCurrentWeather(isMetric) }

    val weatherLocation by lazyDeferred { forecastRepository.getWeatherLocation() }

}