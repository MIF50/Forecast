package com.mif50.forecast.data.remote.model

import com.google.gson.annotations.SerializedName
import com.mif50.forecast.data.db.entity.FutureWeatherEntry

data class ForecastDaysContainer(@SerializedName("forecastday") val entries: List<FutureWeatherEntry>)