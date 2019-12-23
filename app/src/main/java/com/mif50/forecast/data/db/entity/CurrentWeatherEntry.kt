package com.mif50.forecast.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val current_weather_id = 0

@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(
        @SerializedName("cloudcover")
        val cloudCover: Int,

        @SerializedName("feelslike")
        val feelsLike: Int,

        @SerializedName("humidity")
        val humidity: Int,

        @SerializedName("is_day")
        val isDay: String,

        @SerializedName("observation_time")
        val observationTime: String,

        @SerializedName("precip")
        val precIp: Double,

        @SerializedName("pressure")
        val pressure: Int,

        @SerializedName("temperature")
        val temperature: Int,

        @SerializedName("uv_index")
        val uvIndex: Int,

        @SerializedName("visibility")
        val visibility: Int,

        @SerializedName("weather_code")
        val weatherCode: Int,

        @SerializedName("weather_descriptions")
        val weatherDescriptions: List<String>,

        @SerializedName("weather_icons")
        val weatherIcons: List<String>,

        @SerializedName("wind_degree")
        val windDegree: Int,

        @SerializedName("wind_dir")
        val windDir: String,

        @SerializedName("wind_speed")
        val windSpeed: Int
) {

    @PrimaryKey(autoGenerate = false)
    var id = current_weather_id
}

data class Condition(
        @SerializedName("code")
        val code: Int?,

        @SerializedName("icon")
        val icon: String,

        @SerializedName("text")
        val text: String
)