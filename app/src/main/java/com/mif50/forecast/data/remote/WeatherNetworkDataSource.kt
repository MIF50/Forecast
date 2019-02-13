package com.mif50.forecast.data.remote

import androidx.lifecycle.LiveData
import com.mif50.forecast.data.remote.model.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(location: String, languageCode: String)

}