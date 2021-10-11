package com.subiyantoro.panintitest.di

import android.content.Context
import androidx.room.Room
import com.subiyantoro.panintitest.datasource.ApiSourceData
import com.subiyantoro.panintitest.datasource.ApiSourceDataImpl
import com.subiyantoro.panintitest.datasource.LocalSourceData
import com.subiyantoro.panintitest.datasource.LocalSourceDataImpl
import com.subiyantoro.panintitest.repository.MainRepository
import com.subiyantoro.panintitest.repository.MainRepositoryImpl
import com.subiyantoro.panintitest.service.ApiService
import com.subiyantoro.panintitest.service.AppDatabase
import com.subiyantoro.panintitest.service.LocalService
import com.subiyantoro.panintitest.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    fun apiWeatherHandler(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideApiSourceData(apiService: ApiService): ApiSourceData {
        return ApiSourceDataImpl(apiService)
    }

    @Provides
    fun provideLocalService(@ApplicationContext context: Context): LocalService {
        return Room.databaseBuilder(context, AppDatabase::class.java, "Weather")
            .build().dbService()
    }

    @Provides
    fun provideLocalSourceData(localService: LocalService): LocalSourceData {
        return LocalSourceDataImpl(localService)
    }

    @Provides
    fun provideMainRepository(apiService: ApiService, localService: LocalService): MainRepository {
        return MainRepositoryImpl(apiService, localService)
    }
}