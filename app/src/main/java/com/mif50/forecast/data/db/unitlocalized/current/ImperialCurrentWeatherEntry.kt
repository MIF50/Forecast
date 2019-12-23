package com.mif50.forecast.data.db.unitlocalized.current

import androidx.room.ColumnInfo

data class ImperialCurrentWeatherEntry(
        @ColumnInfo(name = "temperature")
        override val temperature: Double,

        @ColumnInfo(name = "weatherDescriptions")
        override val conditionText: String,

        @ColumnInfo(name = "weather_icons")
        override val conditionIconUrl: String,

        @ColumnInfo(name = "windSpeed")
        override val windSpeed: Double,

        @ColumnInfo(name = "windDir")
        override val windDirection: String,

        @ColumnInfo(name = "precIp")
        override val precipitationVolume: Double,

        @ColumnInfo(name = "feelslikeF")
        override val feelsLikeTemperature: Double,

        @ColumnInfo(name = "visibility")
        override val visibilityDistance: Double

) : UnitSpecificCurrentWeatherEntry