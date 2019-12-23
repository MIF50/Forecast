package com.mif50.forecast.data.remote.model

import com.google.gson.annotations.SerializedName
import com.mif50.forecast.data.db.entity.CurrentWeatherEntry
import com.mif50.forecast.data.db.entity.WeatherLocation


data class CurrentWeatherResponse(

    @SerializedName("current")
    val currentEntry: CurrentWeatherEntry,

    @SerializedName("location")
    val location: WeatherLocation

)



