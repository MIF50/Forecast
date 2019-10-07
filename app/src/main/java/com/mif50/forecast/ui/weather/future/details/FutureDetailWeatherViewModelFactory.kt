package com.mif50.forecast.ui.weather.future.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mif50.forecast.data.provider.UnitProvider
import com.mif50.forecast.data.repository.ForecastRepository
import org.threeten.bp.LocalDate

class FutureDetailWeatherViewModelFactory(
        private val detailDate: LocalDate,
        private val forecastRepository: ForecastRepository,
        private val unitProvider: UnitProvider) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FutureDetailWeatherViewModel(detailDate, forecastRepository, unitProvider) as T
    }
}