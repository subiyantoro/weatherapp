package com.subiyantoro.panintitest.datasource

import com.subiyantoro.panintitest.models.City
import com.subiyantoro.panintitest.models.WeatherResponse

interface LocalSourceData {
    suspend fun getListCity(): List<WeatherResponse>
    suspend fun deleteWeather(weatherResponse: WeatherResponse)
    suspend fun saveNewCity(weatherResponse: WeatherResponse)
}