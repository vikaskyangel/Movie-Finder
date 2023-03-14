package com.nuwm.moviefinder

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nuwm.moviefinder.models.Movie
import com.nuwm.moviefinder.models.MovieDetails

class DetailMoviePagerAdapter(fa: FragmentActivity, movie: MovieDetails) : FragmentStateAdapter(fa) {
    val fragmentList: List<Pair<Fragment, String>> = listOf(
        AboutMovieFragment.newInstance(movie.overview) to "About Movie",
        CastMovieFragment.newInstance(movie.id.toString()) to "Cast",
    )

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position].first
}