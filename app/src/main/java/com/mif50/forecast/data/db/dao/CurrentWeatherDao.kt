package com.mif50.forecast.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mif50.forecast.data.db.entity.CurrentWeatherEntry
import com.mif50.forecast.data.db.entity.current_weather_id
import com.mif50.forecast.data.db.unitlocalized.current.ImperialCurrentWeatherEntry
import com.mif50.forecast.data.db.unitlocalized.current.MetricCurrentWeatherEntry

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntryEntry: CurrentWeatherEntry)

    @Query("select * from current_weather where id = $current_weather_id")
    fun getWeatherMetric(): LiveData<MetricCurrentWeatherEntry>

    @Query("select * from current_weather where id = $current_weather_id")
    fun getWeatherImperial(): LiveData<ImperialCurrentWeatherEntry>

}