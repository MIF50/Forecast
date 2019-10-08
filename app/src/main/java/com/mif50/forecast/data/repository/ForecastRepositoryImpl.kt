package com.mif50.forecast.data.repository

import androidx.lifecycle.LiveData
import com.mif50.forecast.data.db.dao.CurrentWeatherDao
import com.mif50.forecast.data.db.dao.FutureWeatherDao
import com.mif50.forecast.data.db.dao.WeatherLocationDao
import com.mif50.forecast.data.db.entity.WeatherLocation
import com.mif50.forecast.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.mif50.forecast.data.db.unitlocalized.future.detail.UnitSpecificDetailFutureWeatherEntry
import com.mif50.forecast.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import com.mif50.forecast.data.provider.LocationProvider
import com.mif50.forecast.data.remote.WeatherNetworkDataSource
import com.mif50.forecast.data.remote.model.CurrentWeatherResponse
import com.mif50.forecast.data.remote.model.FutureWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(

    private val currentWeatherDao: CurrentWeatherDao,
    private val futureWeatherDao: FutureWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider

) : ForecastRepository {

    init {
        weatherNetworkDataSource.apply {
            // when data in downloadedCurrentWeather (live data) changed save in persist current weather
            downloadedCurrentWeather.observeForever { newCurrentWeather ->
                persistFetchedCurrentWeather(newCurrentWeather)
            }

            // when data in downloadedFutureWeather (live data) changed save in persist future weather
            downloadedFutureWeather.observeForever { newFutureWeather ->
                persistFetchedFutureWeather(newFutureWeather)
            }

        }
    }

    /**
     *  we need to get future weather List
     *  first check weather data first time or need to update
     *  fetch future weather List form RoomDatabase by isMetric or not
     * */

    override suspend fun getFutureWeatherList(
        startDate: LocalDate,
        metric: Boolean
    ): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>> {

        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext if (metric) futureWeatherDao.getSimpleWeatherForecastsMetric(
                startDate
            )
            else futureWeatherDao.getSimpleWeatherForecastsImperial(startDate)
        }

    }

    /**
     *  get weather location form RoomDatabase
     * */
    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO) {
            return@withContext weatherLocationDao.getLocation()
        }
    }

    /**
     *  we need to get future weather by data
     *  first check weather data first time or need to update
     *  fetch future weather by date form RoomDatabase by isMetric or not
     * */
    override suspend fun getFutureWeatherByDate(
        date: LocalDate,
        metric: Boolean
    ): LiveData<out UnitSpecificDetailFutureWeatherEntry> {

        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext if (metric) futureWeatherDao.getDetailedWeatherByDateMetric(date)
            else futureWeatherDao.getDetailedWeatherByDateImperial(date)
        }
    }

    /**
     * check that this first time to fetch weather location or location has been changed
     * if that fetch current weather and fetch future weather
     * if not
     * check if we need to update current weather or not it update ever 30 minute
     * */
    private suspend fun initWeatherData() {

        val lastWeatherLocation = weatherLocationDao.getLocationNonLive()
        if (lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation)) {
            fetchCurrentWeather()
            fetchFutureWeather()
            return
        }
        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()

    }

    /**
     *  persist fetched future weather in RoomDatabase
     * */
    private fun persistFetchedFutureWeather(fetchedWeather: FutureWeatherResponse) {
        // first delete old data form room data base
        fun deleteOldForecastData() {
            val today = LocalDate.now()
            futureWeatherDao.deleteOldEntries(today)
        }

        GlobalScope.launch(Dispatchers.IO) {
            deleteOldForecastData()
            val futureWeatherList = fetchedWeather.futureWeatherEntries.entries
            futureWeatherDao.insert(futureWeatherList)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }


    /**
     *  return current Weather form RoomDatabase depend on is metric or not
     * */
    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext if (metric) currentWeatherDao.getWeatherMetric() else currentWeatherDao.getWeatherImperial()
        }

    }

    /**
     * save fetchWeather in RoomDatabase current weather and weather location
     * */
    private fun persistFetchedCurrentWeather(fetchWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchWeather.currentEntry)
            weatherLocationDao.upsert(fetchWeather.location)
        }
    }

    /**
     * fetch current weather form api and add data in live data  downloadedCurrentWeather
     * */
    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocationString(),
            Locale.getDefault().language
        )
    }

    /**
     * fetch future weather form api and add data in live data  downloadedFutureWeather
     * */
    private suspend fun fetchFutureWeather() {
        weatherNetworkDataSource.fetchFutureWeather(
            locationProvider.getPreferredLocationString(),
            Locale.getDefault().language
        )
    }

    /**
     *  check that @param lastFetchTime  is before thirty minutes ago
     *  @return True or false
     * */
    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

}