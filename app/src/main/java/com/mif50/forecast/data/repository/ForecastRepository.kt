package com.mif50.forecast.data.repository

import androidx.lifecycle.LiveData
import com.mif50.forecast.data.db.entity.WeatherLocation
import com.mif50.forecast.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository{
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
}