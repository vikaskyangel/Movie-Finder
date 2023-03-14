package com.nuwm.moviefinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment


class AboutMovieFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_about_movie, container, false)
        view.findViewById<TextView>(R.id.aboutMovie).text = arguments?.getString(ARG_ABOUT)
        return view
    }

    companion object {

        const val ARG_ABOUT = "about"

        @JvmStatic
        fun newInstance(about: String) =
            AboutMovieFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_ABOUT, about)
                }
            }
    }
}