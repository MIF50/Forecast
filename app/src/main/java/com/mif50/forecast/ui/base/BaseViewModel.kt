package com.mif50.forecast.ui.base

import androidx.lifecycle.ViewModel
import com.mif50.forecast.data.provider.UnitProvider
import com.mif50.forecast.data.repository.ForecastRepository
import com.mif50.forecast.internal.UnitSystem
import com.mif50.forecast.internal.lazyDeferred

abstract class BaseViewModel(
        private val forecastRepository: ForecastRepository,
        unitProvider: UnitProvider) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit: Boolean get() = unitSystem == UnitSystem.METRIC

    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }
}