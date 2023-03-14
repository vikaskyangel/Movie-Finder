package com.nuwm.moviefinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nuwm.moviefinder.models.Movie


class MovieRecyclerAdapter(
    var movieList: List<Movie> = emptyList(),
    val itemClick: (Movie) -> Unit
) :
    RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(v, itemClick)
    }

    fun setData(movieList: List<Movie>) {
        this.movieList = movieList
        notifyItemInserted(0)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.setItem(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    class MovieViewHolder(itemView: View, var itemClick: (Movie) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        fun setItem(movie: Movie) {
            itemView.setOnClickListener {
                itemClick.invoke(movie)
            }
            val imageView = itemView.findViewById<ImageView>(R.id.image)
            val title = itemView.findViewById<TextView>(R.id.title)
            val stars = itemView.findViewById<TextView>(R.id.stars)
            title.text = movie.title
            stars.text = movie.vote_average.toString()
            val imageUrl = "https://image.tmdb.org/t/p/w533_and_h300_bestv2${movie.backdrop_path}"
            Glide
                .with(itemView.context)
                .load(imageUrl)
                .centerCrop()
                .into(imageView)
        }
    }
}