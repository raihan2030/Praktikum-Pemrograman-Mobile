package com.pemrogramanmobile.apicompose.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1
    ): MovieListResponse

     @GET("movie/{movie_id}")
     suspend fun getMovieDetails(
         @Path("movie_id") movieId: Int
     ): MovieApiModel
}