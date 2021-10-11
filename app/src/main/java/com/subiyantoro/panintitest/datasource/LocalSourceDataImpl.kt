package com.subiyantoro.panintitest.datasource

import com.subiyantoro.panintitest.models.City
import com.subiyantoro.panintitest.models.WeatherResponse
import com.subiyantoro.panintitest.service.LocalService

class LocalSourceDataImpl(var localService: LocalService): LocalSourceData {
    override suspend fun getListCity(): List<WeatherResponse> = localService.getListCity()
    override suspend fun deleteWeather(weatherResponse: WeatherResponse) = localService.deleteWeather(weatherResponse)
    override suspend fun saveNewCity(weatherResponse: WeatherResponse) = localService.insertNewCity(weatherResponse)
}