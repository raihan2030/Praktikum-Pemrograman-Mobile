package com.pemrogramanmobile.apicompose.domain.usecase

import com.pemrogramanmobile.apicompose.domain.model.Movie
import com.pemrogramanmobile.apicompose.domain.repository.MovieRepository
import com.pemrogramanmobile.apicompose.util.NetworkResult
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase(private val movieRepository: MovieRepository) {
    operator fun invoke(): Flow<NetworkResult<List<Movie>>> {
        return movieRepository.getPopularMovies()
    }
}