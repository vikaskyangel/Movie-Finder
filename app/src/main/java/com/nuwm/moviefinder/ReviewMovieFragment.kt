package com.nuwm.moviefinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nuwm.moviefinder.models.MovieList
import com.nuwm.moviefinder.network.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReviewMovieFragment : Fragment() {

    private lateinit var recyclerAdapter: MovieTabRecyclerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_tab_movie_list, container, false)
        initList(view.findViewById(R.id.movieList))
        getMovies(arguments?.getSerializable(ARG_MOVIE_TYPE) as MovieType)
        return view
    }

    private fun getMovies(movieType: MovieType) {
        when (movieType) {
            MovieType.NOW_PALAY -> {
                getNewMoviesList()
            }
            MovieType.UPCOMING -> {
                getUpcommingMoviesList()
            }
            MovieType.TOP -> {
                getTopMoviesList()
            }
            MovieType.POPULAR -> {
                getPopularMoviesList()
            }
        }
    }

    private fun getTopMoviesList() {
        RetrofitUtil.moviesApi.getTopMovie(1).enqueue(object : Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                if (response.isSuccessful) {
                    response.body()?.results?.let { recyclerAdapter.setData(it) }
                } else {
                    Toast.makeText(requireContext(), "Error while getMovies", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Error while getMovies ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun getPopularMoviesList() {
        RetrofitUtil.moviesApi.getPopularMovie(1).enqueue(object : Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                if (response.isSuccessful) {
                    response.body()?.results?.let { recyclerAdapter.setData(it) }
                } else {
                    Toast.makeText(requireContext(), "Error while getMovies", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Error while getMovies ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun getUpcommingMoviesList() {
        RetrofitUtil.moviesApi.getUpcomingMovie(1).enqueue(object : Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                if (response.isSuccessful) {
                    response.body()?.results?.let { recyclerAdapter.setData(it) }
                } else {
                    Toast.makeText(requireContext(), "Error while getMovies", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Error while getMovies ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun getNewMoviesList() {
        RetrofitUtil.moviesApi.getNewMovie(1).enqueue(object : Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                if (response.isSuccessful) {
                    response.body()?.results?.let { recyclerAdapter.setData(it) }
                } else {
                    Toast.makeText(requireContext(), "Error while getMovies", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Error while getMovies ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun initList(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.movieList)
        recyclerAdapter = MovieTabRecyclerAdapter {
            DetailMovieActivity.newIntent(requireContext(), it.id.toString())
        }
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
    }


    companion object {

        const val ARG_MOVIE_TYPE = "MovieType"

        @JvmStatic
        fun newInstance(type: MovieType) =
            ReviewMovieFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_MOVIE_TYPE, type)
                }
            }
    }
}