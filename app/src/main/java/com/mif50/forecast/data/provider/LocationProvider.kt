package com.mif50.forecast.data.provider

import com.mif50.forecast.data.db.entity.WeatherLocation

interface LocationProvider{
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPreferredLocationString(): String
}