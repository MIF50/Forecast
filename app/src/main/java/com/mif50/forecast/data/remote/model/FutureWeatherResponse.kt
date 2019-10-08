package com.mif50.forecast.data.remote.model
import com.google.gson.annotations.SerializedName
import com.mif50.forecast.data.db.entity.WeatherLocation

data class FutureWeatherResponse(
        @SerializedName("forecast")
        val futureWeatherEntries: ForecastDaysContainer,

        val location: WeatherLocation

)