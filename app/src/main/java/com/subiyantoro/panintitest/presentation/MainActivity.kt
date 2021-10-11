package com.subiyantoro.panintitest.presentation

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.subiyantoro.panintitest.R
import com.subiyantoro.panintitest.databinding.ActivityMainBinding
import com.subiyantoro.panintitest.models.WeatherResponse
import com.subiyantoro.panintitest.utils.Constant
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnClickWeather {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: WeatherAdapter
    private val mainViewModel by viewModels<MainViewModel>()
    private var listWeatherApi: List<WeatherResponse> = listOf()
    private var listWeatherLocal: List<WeatherResponse> = listOf()
    private var isEdit: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRV()
        setupToolbar()
        dialogBuilderAndListener()
        mainViewModel.getDataOffline()
        dataListener()
        searchListener()
    }

    private fun setupRV() {
        adapter = WeatherAdapter(this)
        binding.rvWeather.adapter = adapter
        binding.rvWeather.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.edit_act -> {
                isEdit = !isEdit
                adapter.showBtnRemove(isEdit)
            }
            R.id.filter_act -> {
                val dialogSort = AlertDialog.Builder(this)
                val inflater: LayoutInflater = this.layoutInflater
                val sortOption: View = inflater.inflate(R.layout.filter_sort, null)
                val sortGroup: RadioGroup = sortOption.findViewById(R.id.sort_menu)
                dialogSort.setView(sortOption)
                dialogSort.setTitle("Filter Weather")
                val initDialog = dialogSort.create()
                initDialog.show()
                sortGroup.setOnCheckedChangeListener { group, checkedId ->
                    when(resources.getResourceEntryName(checkedId)) {
                        "sort_az" -> {
                            sortAZ()
                            initDialog.dismiss()
                        }
                        "sort_za" -> {
                            sortZA()
                            initDialog.dismiss()
                        }
                    }
                }
            }
            R.id.refresh_act -> mainViewModel.getDataOffline()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sortZA() {
        if (listWeatherApi.isNotEmpty()) {
            val sortedZA = listWeatherApi.sortedByDescending { it.name }
            adapter.updateWeatherList(sortedZA)
        } else {
            val sortedZA = listWeatherLocal.sortedByDescending { it.name }
            adapter.updateWeatherList(sortedZA)
        }
    }

    private fun sortAZ() {
        if (listWeatherApi.isNotEmpty()) {
            val sortedAZ = listWeatherApi.sortedBy { it.name }
            adapter.updateWeatherList(sortedAZ)
        } else {
            val sortedZA = listWeatherLocal.sortedBy { it.name }
            adapter.updateWeatherList(sortedZA)
        }
    }

    private fun dataListener() {
        mainViewModel.listWeatherOnline.observe(this) {
            listWeatherApi = it ?: ArrayList()
            adapter.updateWeatherList(listWeatherApi)
        }
        mainViewModel.listWeatherOffline.observe(this) {
            Log.i("TAG", "dataListener: $it")
            if (it.isEmpty()) {
                binding.textEmpty.visibility = View.VISIBLE
                adapter.updateWeatherList(it)
            } else {
                binding.textEmpty.visibility = View.GONE
                listWeatherLocal = it
                adapter.updateWeatherList(it)
                mainViewModel.getListDataOnline(it)
            }
        }
    }

    private fun dialogBuilderAndListener() {
        binding.addCity.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val inflater: LayoutInflater = this.layoutInflater
            val inputView: View = inflater.inflate(R.layout.add_city_layout, null)
            val editText: EditText = inputView.findViewById(R.id.form_add_city)
            if (inputView.parent != null) {
                (inputView.parent as ViewGroup).removeView(inputView)
            }
            dialog.setView(inputView)
            dialog.setTitle("Add New City Weather")
            dialog.setMessage("Don't empty this form if you wanna add city")
                .setPositiveButton("Save", DialogInterface.OnClickListener { dialog, which ->
                    val cityValue = editText.text.toString()
                    if (cityValue == "")
                        editText.error = "Form Cannot be Empty"
                    else {
                        mainViewModel.getDataWeatherOnline(cityValue)?.let { wr ->
                            mainViewModel.saveWeather(
                                wr
                            )
                        }
                        dialog.dismiss()
//                        Log.i("TAG", "dialogBuilderAndListener: $listWeatherApi")
                    }
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                })
            dialog.show()
        }
    }

    private fun searchListener() {
        binding.editText.doAfterTextChanged {
            if (it?.length != 0) {
                if (listWeatherApi.isNotEmpty()) {
                    val results = listWeatherApi.filter { wp -> wp.name.contains(it.toString(), ignoreCase = true) }
                    adapter.updateWeatherList(results)
                }
                else {
                    val results = listWeatherLocal.filter { wp -> wp.name.contains(it.toString(), ignoreCase = true) }
                    adapter.updateWeatherList(results)
                }
            } else {
                if (listWeatherApi.isNotEmpty()) adapter.updateWeatherList(listWeatherApi) else adapter.updateWeatherList(listWeatherLocal)
            }
        }
    }

    override fun onClickItemWeather(weatherResponse: WeatherResponse) {
        val goToDetail = Intent(this, DetailWeatherActivity::class.java)
        goToDetail.putExtra(Constant.WEATHER_DATA, weatherResponse)
        startActivity(goToDetail)
    }

    override fun removeWeather(weatherResponse: WeatherResponse) {
        mainViewModel.deleteWeather(weatherResponse)
        listWeatherApi.drop(listWeatherApi.indexOf(weatherResponse))
        listWeatherLocal.drop(listWeatherApi.indexOf(weatherResponse))
    }
}