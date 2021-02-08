package com.brn.moveekmm.shared.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieModel (
    val popularity: Double,
    val id: Int,
    val video: Boolean,
    val adult: Boolean,
    val overview: String,
    val title: String,

    @SerialName("vote_count")
    val voteCount: Int,

    @SerialName("vote_average")
    val voteAverage: Double,

    @SerialName("release_date")
    val releaseDate: String,

    @SerialName("original_language")
    val originalLanguage: String,

    @SerialName("original_title")
    val originalTitle: String,

    @SerialName("backdrop_path")
    val backdropPath: String,

    @SerialName("poster_path")
    val posterPath: String
)