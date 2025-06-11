package com.pemrogramanmobile.apicompose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies ORDER BY voteAverage DESC")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieById(movieId: Int): Flow<MovieEntity?>

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()

    @Query("SELECT COUNT(*) FROM movies")
    suspend fun getMovieCount(): Int

    @Query("SELECT lastRefreshed FROM movies ORDER BY lastRefreshed DESC LIMIT 1")
    suspend fun getLastRefreshTimestamp(): Long?
}