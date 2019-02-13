package com.mif50.forecast.ui.weather.future.details


import android.os.Bundle
import com.mif50.forecast.R
import com.mif50.forecast.ui.base.BaseFragment
import com.mif50.forecast.utilies.ui.LayoutRes

@LayoutRes(R.layout.fragment_future_detail_weather)
class FutureDetailWeatherFragment : BaseFragment() {

    companion object {
        fun newInstance()= FutureDetailWeatherFragment().apply {
            arguments = Bundle().apply {
                
            }
        }
    }

    override fun bindView(savedInstanceState: Bundle?) {

    }


}
