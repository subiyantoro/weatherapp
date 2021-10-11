package com.subiyantoro.panintitest.presentation

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subiyantoro.panintitest.models.City
import com.subiyantoro.panintitest.models.WeatherResponse
import com.subiyantoro.panintitest.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var mainRepository: MainRepository): ViewModel() {
    var listWeatherOffline = MutableLiveData<List<WeatherResponse>>()
    var listWeatherOnline = MutableLiveData<MutableList<WeatherResponse>>()
    var isErrorAdding = MutableLiveData(false)

    fun getDataOffline() {
        viewModelScope.launch(Dispatchers.IO) {
            listWeatherOffline.postValue(mainRepository.getListCity())
        }
    }

    private fun getDataOnline(city: String) {
        mainRepository.getWeatherData(city).enqueue(object: Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        viewModelScope.launch(Dispatchers.IO) {
                            mainRepository.saveNewCity(it)
                            listWeatherOnline.value?.add(it)
                            listWeatherOnline.postValue(listWeatherOnline.value)
                        }
                    }
                    isErrorAdding.postValue(false)
                } else {
                    isErrorAdding.postValue(true)
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                isErrorAdding.postValue(true)
            }
        })
    }

    fun getListDataOnline(weatherList: List<WeatherResponse>) {
        listWeatherOnline.postValue(ArrayList())
        weatherList.forEach { weather ->
            getDataOnline(weather.name)
        }
    }

    fun saveWeather(weatherResponse: WeatherResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.saveNewCity(weatherResponse)
        }
    }

    fun getDataWeatherOnline(city: String): WeatherResponse? {
        var responseBody: WeatherResponse? = null
        mainRepository.getWeatherData(city).enqueue(object: Callback<WeatherResponse>{
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    responseBody = response.body()
                }else{
                    Log.i("TAG", "onResponse: ${response.body()}")
                }
            }
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.i("TAG", "onFailure: ${t.message}")
            }
        })
        return responseBody
    }

    fun deleteWeather(weatherResponse: WeatherResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.deleteWeather(weatherResponse)
            this@MainViewModel.getDataOffline()
        }
    }
}