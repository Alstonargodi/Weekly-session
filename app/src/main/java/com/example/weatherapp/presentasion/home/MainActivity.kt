package com.example.weatherapp.presentasion.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.model.utils.ResultRespon
import com.example.weatherapp.presentasion.detailweather.DetailWeatherActivity
import com.example.weatherapp.presentasion.detailweather.DetailWeatherActivity.Companion.idDetail
import com.example.weatherapp.presentasion.vmfactory.ViewModelFactory
import kotlinx.coroutines.launch
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel>{
        ViewModelFactory.getInstance()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        this.setSupportActionBar(binding.toolbarApp)
        binding.toolbarApp.inflateMenu(R.menu.menu_topbar)

        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_topbar,menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                lifecycleScope.launch {
                    if (query != null) {
                        searchWeather(query)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    private suspend fun searchWeather(location: String){
        Log.d("mainactivity","search")
        viewModel.getWeatherData(location).observe(this){ respon->
            when(respon){
                is ResultRespon.Loading ->{

                }
                is ResultRespon.Sucess ->{
                    Log.d("mainactivity respon",respon.data.toString())
                    val intent = Intent(this,DetailWeatherActivity::class.java)
                    intent.putExtra("detailActivity",respon.data)
                    startActivity(intent)
                }
                is ResultRespon.Error ->{
                    Log.d("mainactivity respon",respon.error.toString())
                }
                else -> {}
            }
        }
    }
}