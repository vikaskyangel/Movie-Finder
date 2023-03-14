package com.nuwm.moviefinder

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    val fragmentList: List<Pair<TabMovieFragment, MovieType>> = listOf(
        TabMovieFragment.newInstance(MovieType.NOW_PALAY) to MovieType.NOW_PALAY,
        TabMovieFragment.newInstance(MovieType.UPCOMING) to MovieType.UPCOMING,
        TabMovieFragment.newInstance(MovieType.TOP) to MovieType.TOP,
        TabMovieFragment.newInstance(MovieType.POPULAR) to MovieType.POPULAR
    )

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position].first
}