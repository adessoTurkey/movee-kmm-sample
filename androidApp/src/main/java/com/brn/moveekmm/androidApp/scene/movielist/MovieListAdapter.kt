package com.brn.moveekmm.androidApp.scene.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brn.moveekmm.androidApp.R
import com.brn.moveekmm.shared.models.MovieModel
import com.brn.moveekmm.shared.util.Constants
import com.bumptech.glide.Glide

class MovieListAdapter (private val onClick: (MovieModel) -> Unit):
    RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    var movies: List<MovieModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_movie_item,
                parent,
                false
            ), onClick)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    class MovieViewHolder(itemView: View, onClick: (MovieModel) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val posterView: ImageView = itemView.findViewById(R.id.iv_movie_poster)
        val titleView: TextView = itemView.findViewById(R.id.tv_movie_title)
        private var currentMovie: MovieModel? = null

        init {
            itemView.setOnClickListener {
                currentMovie?.let {
                    onClick(it)
                }
            }
        }

        /* Bind flower name and image. */
        fun bind(movie: MovieModel) {
            currentMovie = movie

            Glide.with(posterView).load(Constants.moviePosterUrl + movie.posterPath).into(posterView)
            titleView.text = movie.title
        }
    }
}