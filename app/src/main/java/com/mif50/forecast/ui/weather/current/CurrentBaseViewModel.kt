package com.mif50.forecast.ui.weather.current

import com.mif50.forecast.data.provider.UnitProvider
import com.mif50.forecast.data.repository.ForecastRepository
import com.mif50.forecast.internal.lazyDeferred
import com.mif50.forecast.ui.base.BaseViewModel

class CurrentBaseViewModel(
        private val forecastRepository: ForecastRepository,
        unitProvider: UnitProvider

) : BaseViewModel(forecastRepository, unitProvider) {


    val weather by lazyDeferred { forecastRepository.getCurrentWeather(super.isMetricUnit) }


}