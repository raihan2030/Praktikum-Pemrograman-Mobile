package com.pemrogramanmobile.apicompose.data.repository

import android.content.Context
import com.pemrogramanmobile.apicompose.data.local.MovieDao
import com.pemrogramanmobile.apicompose.data.remote.TmdbApiService
import com.pemrogramanmobile.apicompose.data.toDomainMovie
import com.pemrogramanmobile.apicompose.data.toDomainMoviesFromEntities
import com.pemrogramanmobile.apicompose.data.toMovieEntities
import com.pemrogramanmobile.apicompose.data.toMovieEntity
import com.pemrogramanmobile.apicompose.domain.model.Movie
import com.pemrogramanmobile.apicompose.domain.repository.MovieRepository
import com.pemrogramanmobile.apicompose.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit

class MovieRepositoryImpl(
    private val tmdbApiService: TmdbApiService,
    private val movieDao: MovieDao,
    private val context: Context
) : MovieRepository {
    private val CACHE_EXPIRY_MS = TimeUnit.HOURS.toMillis(1)

    override fun getPopularMovies(): Flow<NetworkResult<List<Movie>>> = flow {
        val lastRefreshTime = movieDao.getLastRefreshTimestamp() ?: 0L
        val isCacheStale = (System.currentTimeMillis() - lastRefreshTime) > CACHE_EXPIRY_MS
        val movieCount = movieDao.getMovieCount()
        val isCacheEmpty = movieCount == 0

        if (!isCacheEmpty && !isCacheStale) {
            val cachedDataFlow: Flow<List<Movie>> = movieDao.getAllMovies().map { entities ->
                entities.toDomainMoviesFromEntities()
            }
            val cachedData = cachedDataFlow.firstOrNull()
            if (cachedData != null && cachedData.isNotEmpty()) {
                emit(NetworkResult.Success(cachedData))
            }
        }

        if (isCacheEmpty || isCacheStale) {
            try {
                val movieListResponse = tmdbApiService.getPopularMovies()
                val currentTime = System.currentTimeMillis()
                val movieEntities = movieListResponse.results.toMovieEntities(currentTime)

                movieDao.deleteAllMovies()
                movieDao.insertMovies(movieEntities)

                val freshDataFlow: Flow<List<Movie>> = movieDao.getAllMovies().map { entities ->
                    entities.toDomainMoviesFromEntities()
                }
                val freshDataFromDb = freshDataFlow.firstOrNull()

                if (freshDataFromDb != null) {
                    emit(NetworkResult.Success(freshDataFromDb))
                } else {
                    emit(NetworkResult.Error("Gagal mengambil data dari database setelah refresh.", null))
                }
            } catch (e: Exception) {
                val fallbackDataFlow: Flow<List<Movie>> = movieDao.getAllMovies().map { entities ->
                    entities.toDomainMoviesFromEntities()
                }
                val currentCachedData = fallbackDataFlow.firstOrNull()
                emit(NetworkResult.Error("Gagal memuat data dari jaringan: ${e.message}", currentCachedData))
            }
        }
    }

    override fun getMovieDetails(movieId: Int): Flow<NetworkResult<Movie>> = flow {
        val cachedMovieEntity = movieDao.getMovieById(movieId).firstOrNull()
        if (cachedMovieEntity != null) {
            emit(NetworkResult.Success(cachedMovieEntity.toDomainMovie()))
        }

        try {
            val movieApiModel = tmdbApiService.getMovieDetails(movieId)
            val movieEntity = movieApiModel.toMovieEntity(System.currentTimeMillis())

            movieDao.insertMovies(listOf(movieEntity))

            emit(NetworkResult.Success(movieApiModel.toDomainMovie()))
        } catch (e: Exception) {
            if (cachedMovieEntity == null) {
                emit(NetworkResult.Error("Gagal memuat detail film: ${e.message}", null))
            } else {
                emit(NetworkResult.Error("Gagal menyegarkan detail film: ${e.message}", cachedMovieEntity.toDomainMovie()))
            }
        }
    }
}