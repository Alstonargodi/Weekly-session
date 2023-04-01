package com.example.weatherapp.presentasion.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.model.remote.utils.ResultRespon
import com.example.weatherapp.presentasion.detailweather.DetailWeatherActivity
import com.example.weatherapp.presentasion.detailweather.DetailWeatherActivity.Companion.idDetail
import com.example.weatherapp.presentasion.vmfactory.ViewModelFactory
import kotlinx.coroutines.launch
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        this.setSupportActionBar(binding.toolbarApp)
        binding.toolbarApp.inflateMenu(R.menu.menu_topbar)
        ItemTouchHelper(Callback()).attachToRecyclerView(binding.rvFavoriteWeather)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_topbar,menu)
        showFavoriteWeather()
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
        viewModel.getWeatherData(location).observe(this){ respon->
            when(respon){
                is ResultRespon.Loading ->{
                    binding.wait.waitLayout.visibility = View.VISIBLE
                }
                is ResultRespon.Sucess ->{
                    binding.wait.waitLayout.visibility = View.GONE
                    val intent = Intent(this,DetailWeatherActivity::class.java)
                    intent.putExtra("detailActivity",respon.data)
                    startActivity(intent)
                }
                is ResultRespon.Error ->{
                    binding.wait.waitLayout.visibility = View.GONE
                    Log.d("mainactivity respon",respon.error.toString())
                    binding.problem.problemLayout.visibility = View.VISIBLE
                }
                else -> {}
            }
        }
    }

    private fun showFavoriteWeather(){
        viewModel.readFavorite().observe(this){ favorite ->
            if (favorite.isNotEmpty()){
                binding.empty.emptyConstraint.visibility = View.GONE
                val adapter = FavoriteAdapter(favorite)
                val recView = binding.rvFavoriteWeather
                recView.adapter = adapter
                recView.layoutManager = LinearLayoutManager(this)
            }else{
                binding.empty.emptyConstraint.visibility = View.VISIBLE
            }
        }
    }

    inner class Callback : ItemTouchHelper.Callback(){
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
          return makeMovementFlags(0,ItemTouchHelper.RIGHT)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val currentItem = (viewHolder as FavoriteAdapter.ViewHolder).getData()
            Toast.makeText(
                this@MainActivity,
                currentItem.location,
                Toast.LENGTH_LONG
            ).show()
            viewModel.deleteFavorite(currentItem)
        }

    }
}