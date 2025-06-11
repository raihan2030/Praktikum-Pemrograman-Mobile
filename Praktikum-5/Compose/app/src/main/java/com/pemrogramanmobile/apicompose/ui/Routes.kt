package com.pemrogramanmobile.apicompose.ui

object Routes {
    const val MOVIE_LIST = "movieList"
    const val MOVIE_DETAIL = "movieDetail/{movieId}"

    fun navigateById(id: Int): String{
        return "movieDetail/${id}"
    }
}