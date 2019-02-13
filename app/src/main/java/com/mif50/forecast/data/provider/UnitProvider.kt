package com.mif50.forecast.data.provider

import com.mif50.forecast.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}