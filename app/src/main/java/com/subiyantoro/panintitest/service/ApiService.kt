package com.subiyantoro.panintitest.service

import com.subiyantoro.panintitest.models.WeatherResponse
import com.subiyantoro.panintitest.utils.Constant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    fun getWeather(
        @Query("q") country: String,
        @Query("appid") apiKey: String = Constant.API_KEY
    ): Call<WeatherResponse>
}