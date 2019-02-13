package com.mif50.forecast

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import com.mif50.forecast.data.db.ForecastDatabase
import com.mif50.forecast.data.provider.LocationProvider
import com.mif50.forecast.data.provider.LocationProviderImpl
import com.mif50.forecast.data.provider.UnitProvider
import com.mif50.forecast.data.provider.UnitProviderImpl
import com.mif50.forecast.data.remote.*
import com.mif50.forecast.data.repository.ForecastRepository
import com.mif50.forecast.data.repository.ForecastRepositoryImpl
import com.mif50.forecast.ui.weather.current.CurrentWeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class App : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@App)) // provide context to use by kodein
        bind() from singleton { ForecastDatabase(instance()) } // instance here refer to context form androidXModule
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) } // instance here refer to context from androidXModule
        bind() from singleton { ApiServices(instance()) } // instance here refer to ConnectivityInterceptor
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) } // instance here refer to ApiService
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(), instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance(), instance(), instance()) } // instance , instance , instance , instance here refer to (CurrentWeatherDao,WeatherNetworkDataSource,WeatherNetworkDataSource,LocationProvider)
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) } // instance refer to context
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }// instance refer to ForecastRepository , UnitProvider
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this) // init time
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}