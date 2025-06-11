package com.pemrogramanmobile.apicompose.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val lastRefreshed: Long = System.currentTimeMillis(),
    val homepage: String? = null
)