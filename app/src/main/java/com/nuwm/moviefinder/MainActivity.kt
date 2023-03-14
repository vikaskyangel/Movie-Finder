package com.nuwm.moviefinder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nuwm.moviefinder.models.MovieList
import com.nuwm.moviefinder.network.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerAdapter: MovieRecyclerAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var viewPager: ViewPager2

    companion object {
        fun newIntent(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.movieList)
        searchView = findViewById(R.id.searchView)
        viewPager = findViewById(R.id.viewPager)
        initUi()
        initRecycler()
        getTopMoviesList()
    }

    private fun initUi() {
        val screenSlidePagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = screenSlidePagerAdapter
        viewPager.offscreenPageLimit = 4
        searchView = findViewById(R.id.searchView)
        val tabLayout = findViewById<TabLayout>(R.id.tabsLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = screenSlidePagerAdapter.fragmentList[position].second.title
        }.attach()
    }

    private fun getTopMoviesList() {
        progressBar.visibility = View.VISIBLE
        RetrofitUtil.moviesApi.getTopMovie(1).enqueue(object : Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    response.body()?.results?.let { recyclerAdapter.setData(it.take(5)) }
                } else {
                    Toast.makeText(this@MainActivity, "Error while getMovies", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(
                    this@MainActivity,
                    "Error while getMovies ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun initRecycler() {
        recyclerAdapter = MovieRecyclerAdapter(itemClick = {
            DetailMovieActivity.newIntent(this, it.id.toString())
        })
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = recyclerAdapter
    }
}