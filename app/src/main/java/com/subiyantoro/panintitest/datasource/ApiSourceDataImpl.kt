package com.subiyantoro.panintitest.datasource

import com.subiyantoro.panintitest.models.WeatherResponse
import com.subiyantoro.panintitest.service.ApiService
import retrofit2.Call

class ApiSourceDataImpl(var apiService: ApiService): ApiSourceData {
    override fun getWeatherData(country: String): Call<WeatherResponse> = apiService.getWeather(country)
}