package com.pemrogramanmobile.apicompose.data

import com.pemrogramanmobile.apicompose.data.local.MovieEntity
import com.pemrogramanmobile.apicompose.data.remote.MovieApiModel
import com.pemrogramanmobile.apicompose.domain.model.Movie

fun MovieApiModel.toDomainMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        homepage = this.homepage
    )
}

fun MovieApiModel.toMovieEntity(lastRefreshedTime: Long): MovieEntity {
    return MovieEntity(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        lastRefreshed = lastRefreshedTime,
        homepage = this.homepage
    )
}

fun MovieEntity.toDomainMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        homepage = this.homepage
    )
}

fun List<MovieApiModel>.toDomainMovies(): List<Movie> {
    return this.map { it.toDomainMovie() }
}

fun List<MovieEntity>.toDomainMoviesFromEntities(): List<Movie> {
    return this.map { it.toDomainMovie() }
}

fun List<MovieApiModel>.toMovieEntities(lastRefreshedTime: Long): List<MovieEntity> {
    return this.map { it.toMovieEntity(lastRefreshedTime) }
}