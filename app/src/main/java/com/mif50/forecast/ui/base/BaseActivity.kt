package com.mif50.forecast.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mif50.forecast.utilies.extension.getLayoutRes

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes().layout)
        bindView(savedInstanceState)
    }

    abstract fun bindView(savedInstanceState: Bundle?)


}