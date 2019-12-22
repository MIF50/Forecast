package com.mif50.forecast.ui.weather.current


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mif50.forecast.R
import com.mif50.forecast.internal.glide.GlideApp
import com.mif50.forecast.ui.base.BaseFragment
import com.mif50.forecast.utilies.ui.LayoutRes
import kotlinx.android.synthetic.main.fragment_current_weather.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

@LayoutRes(R.layout.fragment_current_weather)
class CurrentWeatherFragment : BaseFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()
    private lateinit var viewModel: CurrentBaseViewModel


    override fun bindView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrentBaseViewModel::class.java)
        bindUI()

//        val apiService = ApiServices(ConnectivityInterceptorImpl(this.context!!))
//        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiService)
//        weatherNetworkDataSource.downloadedCurrentWeather.observe(this, Observer {
//            textView.text = it.currentEntry.toString()
//
//        })
//
//        GlobalScope.launch(Dispatchers.Main) {
//            //            val currentWeatherResponse = apiService.getCurrentWeather("London").await()
////            textView.text = currentWeatherResponse.toString()
//            weatherNetworkDataSource.fetchCurrentWeather("Los Angeles", "en")
//        }
    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()

        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(this@CurrentWeatherFragment, Observer {
            if (it == null) return@Observer
                (activity as? AppCompatActivity)?.let { activity ->
                    activity.supportActionBar?.title = it.name
                    activity.supportActionBar?.subtitle = "Today"
                }

        })
        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if (it == null) return@Observer
            groupLoading.visibility = View.GONE


            val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
            temperatureTv.text = "${it.temperature}$unitAbbreviation"
            temperatureFeelsLikeTv.text = "Feels like ${it.feelsLikeTemperature}$unitAbbreviation"
            conditionTv.text = it.conditionText
            precipitationTV.text =
                "Preciptiation: ${it.precipitationVolume}${chooseLocalizedUnitAbbreviation("mm", "in")}"
            windTv.text = "Wind: ${it.windDirection}, ${it.windSpeed} ${chooseLocalizedUnitAbbreviation("kph", "mph")}"
            visibilityTV.text =
                "Visibility: ${it.visibilityDistance}, ${it.windSpeed} ${chooseLocalizedUnitAbbreviation("km", "mi")}"

            GlideApp.with(this@CurrentWeatherFragment)
                .load("http:${it.conditionIconUrl}")
                .into(conditionIv)

        })
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        return if (viewModel.isMetricUnit) metric else imperial
    }

}
