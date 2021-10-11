package com.subiyantoro.panintitest.service

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.subiyantoro.panintitest.models.City
import com.subiyantoro.panintitest.models.WeatherResponse

@Dao
interface LocalService {
    @Query("SELECT * FROM Weather")
    fun getListCity(): List<WeatherResponse>

    @Insert(onConflict = REPLACE)
    suspend fun insertNewCity(weatherResponse: WeatherResponse)

    @Delete
    suspend fun deleteWeather(weatherResponse: WeatherResponse)
}