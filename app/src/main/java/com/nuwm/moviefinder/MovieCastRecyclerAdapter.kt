package com.nuwm.moviefinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nuwm.moviefinder.models.Cast
import com.nuwm.moviefinder.models.Movie


class MovieCastRecyclerAdapter(
    var movieList: List<Cast> = emptyList(),
    val itemClick: (Cast) -> Unit
) :
    RecyclerView.Adapter<MovieCastRecyclerAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cast, parent, false)
        return MovieViewHolder(v, itemClick)
    }

    fun setData(movieList: List<Cast>) {
        this.movieList = movieList
        notifyItemInserted(0)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.setItem(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    class MovieViewHolder(itemView: View, var itemClick: (Cast) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        fun setItem(cast: Cast) {
            itemView.setOnClickListener {
                itemClick.invoke(cast)
            }
            val imageView = itemView.findViewById<ImageView>(R.id.image)
            val name = itemView.findViewById<TextView>(R.id.name)
            name.text = cast.name
            val imageUrl = "https://image.tmdb.org/t/p/w533_and_h300_bestv2${cast.profile_path}"
            Glide
                .with(itemView.context)
                .load(imageUrl)
                .centerCrop()
                .into(imageView)
        }
    }
}