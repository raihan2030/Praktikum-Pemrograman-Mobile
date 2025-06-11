package com.pemrogramanmobile.apicompose.domain.repository

import com.pemrogramanmobile.apicompose.domain.model.Movie
import com.pemrogramanmobile.apicompose.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<NetworkResult<List<Movie>>>
    fun getMovieDetails(movieId: Int): Flow<NetworkResult<Movie>>
}