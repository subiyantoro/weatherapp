package com.subiyantoro.panintitest.presentation

import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.subiyantoro.panintitest.R
import com.subiyantoro.panintitest.databinding.ActivityDetailWeatherBinding
import com.subiyantoro.panintitest.models.WeatherResponse
import com.subiyantoro.panintitest.utils.Constant

class DetailWeatherActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailWeatherBinding
    lateinit var dataWeather: WeatherResponse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailWeatherBinding.inflate(layoutInflater)
        dataWeather = intent.getSerializableExtra(Constant.WEATHER_DATA) as WeatherResponse
        setContentView(binding.root)
        setupToolbar()
        setupDataToView()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = dataWeather.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.navigationIcon?.setColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
    }

    private fun setupDataToView() {
        Glide.with(this).load("${Constant.URL_IMAGE}${dataWeather.weather?.get(0)?.icon}.png").into(binding.imageWeather)
        binding.valueWeather.text = if (dataWeather.main == null) "-" else "${String.format("%.1f", dataWeather.main?.temp?.minus(273.15)).toDouble()} \u2103"
        binding.windSpeed.text = "${dataWeather.wind?.speed.toString()} km/h"
        binding.humidity.text = "${dataWeather.main?.humidity} %"
        binding.cloud.text = dataWeather.clouds?.all.toString()
        binding.textView.text = dataWeather.weather?.get(0)?.description
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}