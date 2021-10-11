package com.subiyantoro.panintitest.presentation

import com.subiyantoro.panintitest.models.WeatherResponse

interface OnClickWeather {
    fun onClickItemWeather(weatherResponse: WeatherResponse)
    fun removeWeather(weatherResponse: WeatherResponse)
}