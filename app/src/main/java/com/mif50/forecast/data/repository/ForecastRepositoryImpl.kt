package com.mif50.forecast.data.repository

import androidx.lifecycle.LiveData
import com.mif50.forecast.data.db.dao.CurrentWeatherDao
import com.mif50.forecast.data.db.dao.WeatherLocationDao
import com.mif50.forecast.data.db.entity.WeatherLocation
import com.mif50.forecast.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.mif50.forecast.data.provider.LocationProvider
import com.mif50.forecast.data.remote.WeatherNetworkDataSource
import com.mif50.forecast.data.remote.model.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
        private val currentWeatherDao: CurrentWeatherDao,
        private val weatherLocationDao: WeatherLocationDao,
        private val weatherNetworkDataSource: WeatherNetworkDataSource,
        private val locationProvider: LocationProvider) : ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            // persist
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO) {
            return@withContext weatherLocationDao.getLocation()
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherDate()
            return@withContext if (metric) currentWeatherDao.getWeatherMetric() else currentWeatherDao.getWeatherImperial()
        }

    }

    private fun persistFetchedCurrentWeather(fetchWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchWeather.currentEntry)
            weatherLocationDao.upsert(fetchWeather.location)
        }
    }

    private suspend fun initWeatherDate() {

        val lastWeatherLocation = weatherLocationDao.getLocation().value
        if (lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation)) {
            fetchCurrentWeather()
            return
        }
        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()

    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather(locationProvider.getPreferredLocationString(), Locale.getDefault().language)
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}