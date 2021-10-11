package com.subiyantoro.panintitest.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.subiyantoro.panintitest.databinding.WeatherItemBinding
import com.subiyantoro.panintitest.models.WeatherResponse
import com.subiyantoro.panintitest.utils.Constant

class WeatherAdapter(var onClickWeather: OnClickWeather): RecyclerView.Adapter<WeatherAdapter.WeatherItemHolder>() {
    private var listWeather = listOf<WeatherResponse>()
    private var isEdit: Boolean = false
    inner class WeatherItemHolder(var binding: WeatherItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherResponse: WeatherResponse) {
            binding.cityName.text = weatherResponse.name
            binding.weatherStatus.text = if (weatherResponse.weather == null) "-" else weatherResponse.weather?.get(0)?.description
            binding.valueWeather.text = if (weatherResponse.main == null) "-" else "${String.format("%.1f", weatherResponse.main?.temp?.minus(273.15)).toDouble()} \u2103"
            Glide.with(binding.root).load("${Constant.URL_IMAGE}${weatherResponse.weather?.get(0)?.icon}.png").into(binding.logoWeather)
            if (isEdit) binding.removeBtn.visibility = View.VISIBLE else binding.removeBtn.visibility = View.GONE
            itemView.setOnClickListener {
                onClickWeather.onClickItemWeather(weatherResponse)
            }
            binding.removeBtn.setOnClickListener {
                onClickWeather.removeWeather(weatherResponse)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherItemHolder {
        val binding = WeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherItemHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherItemHolder, position: Int) {
        holder.bind(listWeather[position])
    }

    override fun getItemCount(): Int = listWeather.size

    fun updateWeatherList(weathers: List<WeatherResponse>) {
        listWeather = weathers
        notifyDataSetChanged()
    }

    fun showBtnRemove(isEdit: Boolean) {
        this.isEdit = isEdit
        notifyDataSetChanged()
    }
}