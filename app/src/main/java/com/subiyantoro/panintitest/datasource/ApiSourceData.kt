package com.subiyantoro.panintitest.datasource

import com.subiyantoro.panintitest.models.WeatherResponse
import retrofit2.Call

interface ApiSourceData {
    fun getWeatherData(country: String): Call<WeatherResponse>
}