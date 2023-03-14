package com.nuwm.moviefinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nuwm.moviefinder.models.CastList
import com.nuwm.moviefinder.models.MovieList
import com.nuwm.moviefinder.network.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CastMovieFragment : Fragment() {

    private lateinit var recyclerAdapter: MovieCastRecyclerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_cast_movie_list, container, false)
        initList(view.findViewById(R.id.castList))
        getCast(arguments?.getString(ARG_MOVIE_ID).orEmpty())
        return view
    }


    private fun getCast(id: String) {
        RetrofitUtil.moviesApi.getCastList(id).enqueue(object : Callback<CastList> {
            override fun onResponse(call: Call<CastList>, response: Response<CastList>) {
                if (response.isSuccessful) {
                    response.body()?.cast?.let { recyclerAdapter.setData(it) }
                } else {
                    Toast.makeText(requireContext(), "Error while getMovies", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<CastList>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Error while getMovies ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun initList(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.castList)
        recyclerAdapter = MovieCastRecyclerAdapter(itemClick = {})
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }


    companion object {

        const val ARG_MOVIE_ID = "MovieID"

        @JvmStatic
        fun newInstance(id: String) =
            CastMovieFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_MOVIE_ID, id)
                }
            }
    }
}