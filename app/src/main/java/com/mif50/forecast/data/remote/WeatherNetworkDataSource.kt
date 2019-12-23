package com.mif50.forecast.data.remote

import androidx.lifecycle.LiveData
import com.mif50.forecast.data.remote.model.CurrentWeatherResponse
import com.mif50.forecast.data.remote.model.FutureWeatherResponse

interface WeatherNetworkDataSource {

    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    suspend fun fetchCurrentWeather(location: String, languageCode: String)

    val downloadedFutureWeather: LiveData<FutureWeatherResponse>
    suspend fun fetchFutureWeather(location: String, languageCode: String)

}