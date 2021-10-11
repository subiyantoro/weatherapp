package com.subiyantoro.panintitest.service

import androidx.room.Database
import androidx.room.RoomDatabase
import com.subiyantoro.panintitest.models.WeatherResponse

@Database(entities = [WeatherResponse::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun dbService(): LocalService
}