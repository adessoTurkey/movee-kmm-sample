package com.brn.moveekmm.shared.network

import com.brn.moveekmm.shared.models.MovieModel
import com.brn.moveekmm.shared.models.PopularMoviesModel
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json as KotlinJson

class TMDbApiClient {

    companion object {
        private const val baseUrl = "https://api.themoviedb.org/"
        private const val apiKey = "YOUR_API_KEY"
        private const val discoverMoviesEndPoint = "3/discover/movie"
        private const val movieDetailEndPoint = "3/movie"
    }

    private val httpApiClient: HttpClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(KotlinJson {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun fetchPopularMovies(): PopularMoviesModel {
        val url = URLBuilder(baseUrl).run {
            path(discoverMoviesEndPoint)
            parameters.append("api_key", apiKey)
            parameters.append("sort_by", "popularity.desc")
            buildString()
        }

        return httpApiClient.get(url)
    }

    suspend fun fetchMovieDetail(movieId: Int): MovieModel {
        val url = URLBuilder(baseUrl).run {
            path(movieDetailEndPoint, movieId.toString())
            parameters.append("api_key", apiKey)
            buildString()
        }

        return httpApiClient.get(url)
    }
}
