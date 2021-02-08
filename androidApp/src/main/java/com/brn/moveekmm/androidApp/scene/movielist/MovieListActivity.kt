package com.brn.moveekmm.androidApp.scene.movielist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brn.moveekmm.androidApp.R
import com.brn.moveekmm.androidApp.scene.moviedetail.MovieDetailActivity
import com.brn.moveekmm.shared.models.MovieModel
import com.brn.moveekmm.shared.network.TMDbApiClient
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

const val MOVIE_ID = "movie_id"

class MovieListActivity : AppCompatActivity() {

    private val mainScope = MainScope()
    private val movieApiClient = TMDbApiClient()
    private val moviesAdapter = MovieListAdapter { movie -> adapterOnClick(movie) }

    private lateinit var moviesRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        setTitle("Movee")

        moviesRecyclerView = findViewById(R.id.rv_movies)

        moviesRecyclerView.apply {
            layoutManager = GridLayoutManager(this@MovieListActivity, 2)
            adapter = moviesAdapter
        }

        getPopularMovies()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }

    private fun getPopularMovies() {
        mainScope.launch {
            kotlin.runCatching {
                movieApiClient.fetchPopularMovies()
            }.onSuccess {
                moviesAdapter.movies = it.results
            }.onFailure {
                Toast.makeText(this@MovieListActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun adapterOnClick(movie: MovieModel) {
        val intent = Intent(this, MovieDetailActivity()::class.java)
        intent.putExtra(MOVIE_ID, movie.id)
        startActivity(intent)
    }
}