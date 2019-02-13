package com.mif50.forecast.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mif50.forecast.data.remote.model.CurrentWeatherResponse
import com.mif50.forecast.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(private val apiServices: ApiServices) : WeatherNetworkDataSource {
    private val _downloadsCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse> get() = _downloadsCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
        try {
            val fetchCurrentWeather = apiServices.getCurrentWeatherAsync(location, languageCode).await()
            _downloadsCurrentWeather.postValue(fetchCurrentWeather)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection", e)
        }
    }
}