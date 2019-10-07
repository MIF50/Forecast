package com.mif50.forecast.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mif50.forecast.data.db.converter.LocalDateConverter
import com.mif50.forecast.data.db.dao.CurrentWeatherDao
import com.mif50.forecast.data.db.dao.FutureWeatherDao
import com.mif50.forecast.data.db.dao.WeatherLocationDao
import com.mif50.forecast.data.db.entity.CurrentWeatherEntry
import com.mif50.forecast.data.db.entity.FutureWeatherEntry
import com.mif50.forecast.data.db.entity.WeatherLocation

@Database(entities = [CurrentWeatherEntry::class,WeatherLocation::class,FutureWeatherEntry::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class ForecastDatabase: RoomDatabase(){
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun weatherLocationDao(): WeatherLocationDao
    abstract fun futureWeatherDao(): FutureWeatherDao

    companion object {
        @Volatile private var instance: ForecastDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context.applicationContext,ForecastDatabase::class.java,"forecast.db")
                .build()
    }
}