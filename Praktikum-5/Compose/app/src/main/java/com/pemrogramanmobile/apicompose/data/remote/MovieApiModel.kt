package com.pemrogramanmobile.apicompose.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListResponse(
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<MovieApiModel>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)

@Serializable
data class MovieApiModel(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("overview") val overview: String,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String?,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("homepage") val homepage: String? = null
)