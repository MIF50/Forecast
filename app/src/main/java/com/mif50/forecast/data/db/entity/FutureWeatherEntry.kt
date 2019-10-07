package com.mif50.forecast.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

import com.google.gson.annotations.SerializedName

@Entity(tableName = "future_weather", indices = [Index(value = ["date"], unique = true)])
data class FutureWeatherEntry(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null,
        val date: String,
        @Embedded
        val day: Day)

data class Day(
        @SerializedName("avgtemp_c")
        val avgtempC: Double,
        @SerializedName("avgtemp_f")
        val avgtempF: Double,
        @SerializedName("avgvis_km")
        val avgvisKm: Double,
        @SerializedName("avgvis_miles")
        val avgvisMiles: Double,
        @Embedded(prefix = "condition_")
        val condition: Condition,
        @SerializedName("maxtemp_c")
        val maxtempC: Double,
        @SerializedName("maxtemp_f")
        val maxtempF: Double,
        @SerializedName("maxwind_kph")
        val maxwindKph: Double,
        @SerializedName("maxwind_mph")
        val maxwindMph: Double,
        @SerializedName("mintemp_c")
        val mintempC: Double,
        @SerializedName("mintemp_f")
        val mintempF: Double,
        @SerializedName("totalprecip_in")
        val totalprecipIn: Double,
        @SerializedName("totalprecip_mm")
        val totalprecipMm: Double,
        val uv: Double )