package com.nuwm.moviefinder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nuwm.moviefinder.models.MovieDetails
import com.nuwm.moviefinder.network.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailMovieActivity : AppCompatActivity() {

    private lateinit var imageMovie: ImageView
    private lateinit var backArrow: ImageView
    private lateinit var image: ImageView
    private lateinit var title: TextView
    private lateinit var stars: TextView
    private lateinit var year: TextView
    private lateinit var duration: TextView
    private lateinit var genre: TextView
    private lateinit var viewPager: ViewPager2

    companion object {
        const val MOVIE_ID_KEY = "movie_id"
        fun newIntent(context: Context, id: String) {
            val intent = Intent(context, DetailMovieActivity::class.java)
            intent.putExtra(MOVIE_ID_KEY, id)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        imageMovie = findViewById(R.id.imageMovie)
        title = findViewById(R.id.title)
        stars = findViewById(R.id.stars)
        backArrow = findViewById(R.id.backArrow)
        viewPager = findViewById(R.id.viewPager)
        year = findViewById(R.id.year)
        duration = findViewById(R.id.duration)
        genre = findViewById(R.id.genre)
        backArrow.setOnClickListener {
            finish()
        }
        image = findViewById<CardView>(R.id.includeMovie).findViewById(R.id.image)
        getMovieById()
    }

    private fun initViewPager(movieDetails: MovieDetails) {
        val pagerAdapter = DetailMoviePagerAdapter(this, movieDetails)
        viewPager.adapter = pagerAdapter
        viewPager.offscreenPageLimit = 3
        val tabLayout = findViewById<TabLayout>(R.id.tabsLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = pagerAdapter.fragmentList[position].second
        }.attach()
    }

    private fun getMovieById() {
        RetrofitUtil.moviesApi.getMovieInfoFromId(intent.getStringExtra(MOVIE_ID_KEY).orEmpty())
            .enqueue(object : Callback<MovieDetails> {
                override fun onResponse(
                    call: Call<MovieDetails>,
                    response: Response<MovieDetails>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.apply {
                            initViewPager(this)
                            initDetails(this)
                        }
                    } else {
                        Toast.makeText(
                            this@DetailMovieActivity,
                            "Error while getMovies",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                    Toast.makeText(
                        this@DetailMovieActivity,
                        "Error while getMovies ${t.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })
    }

    fun initDetails(movieDetails: MovieDetails) {
        val imageUrl =
            "https://image.tmdb.org/t/p/w533_and_h300_bestv2${movieDetails.backdrop_path}"

        val imageUrlPoster =
            "https://image.tmdb.org/t/p/w533_and_h300_bestv2${movieDetails.poster_path}"
        Glide
            .with(imageMovie.context)
            .load(imageUrlPoster)
            .centerCrop()
            .into(imageMovie)
        Glide
            .with(imageMovie.context)
            .load(imageUrl)
            .centerCrop()
            .into(image)

        title.text = movieDetails.title
        stars.text = movieDetails.vote_average.toString()
        year.text = movieDetails.release_date
        duration.text = "${movieDetails.runtime}Minutes"
        genre.text = movieDetails.genres.first().name
    }

}