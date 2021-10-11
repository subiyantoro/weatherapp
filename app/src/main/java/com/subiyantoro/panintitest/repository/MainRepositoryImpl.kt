package com.subiyantoro.panintitest.repository

import com.subiyantoro.panintitest.models.City
import com.subiyantoro.panintitest.models.WeatherResponse
import com.subiyantoro.panintitest.service.ApiService
import com.subiyantoro.panintitest.service.LocalService
import retrofit2.Call

class MainRepositoryImpl(var apiService: ApiService, var localService: LocalService): MainRepository {
    override fun getWeatherData(country: String): Call<WeatherResponse> = apiService.getWeather(country)
    override suspend fun getListCity(): List<WeatherResponse> = localService.getListCity()
    override suspend fun deleteWeather(weatherResponse: WeatherResponse) = localService.deleteWeather(weatherResponse)
    override suspend fun saveNewCity(weatherResponse: WeatherResponse) = localService.insertNewCity(weatherResponse)
}