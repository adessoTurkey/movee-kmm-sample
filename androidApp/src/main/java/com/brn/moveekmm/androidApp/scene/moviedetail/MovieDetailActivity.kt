package com.brn.moveekmm.androidApp.scene.moviedetail

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.brn.moveekmm.androidApp.R
import com.brn.moveekmm.androidApp.scene.movielist.MOVIE_ID
import com.brn.moveekmm.shared.models.MovieModel
import com.brn.moveekmm.shared.network.TMDbApiClient
import com.brn.moveekmm.shared.util.Constants
import com.bumptech.glide.Glide
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MovieDetailActivity : AppCompatActivity() {

    private val mainScope = MainScope()
    private val movieService = TMDbApiClient()
    private lateinit var posterView: ImageView
    private lateinit var movieDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(findViewById(R.id.toolbar))

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val currentMovieId: Int? = bundle.getInt(MOVIE_ID) as? Int
            if (currentMovieId != null) {
                fetchMovieDetail(currentMovieId)
            }
        }

        posterView = findViewById(R.id.iv_movie_detail_poster)
        movieDescription = findViewById(R.id.iv_movie_detail_text)
    }

    private fun fetchMovieDetail(movieId: Int) {
        mainScope.launch {
            kotlin.runCatching {
                movieService.fetchMovieDetail(movieId)
            }.onSuccess {
                setMovieInfo(it)
            }.onFailure {
                Toast.makeText(this@MovieDetailActivity, it.localizedMessage, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun setMovieInfo(movie: MovieModel) {
        setTitle(movie.title)
        movieDescription.text = movie.overview
        Glide.with(posterView).load(Constants.moviePosterUrl + movie.posterPath).into(posterView)
    }
}