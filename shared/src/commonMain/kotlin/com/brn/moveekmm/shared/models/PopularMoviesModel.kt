package com.brn.moveekmm.shared.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopularMoviesModel(
    val page: Int,
    @SerialName("total_results")
    val totalResults: Int,

    @SerialName("total_pages")
    val totalPages: Int,

    val results: List<MovieModel>
)