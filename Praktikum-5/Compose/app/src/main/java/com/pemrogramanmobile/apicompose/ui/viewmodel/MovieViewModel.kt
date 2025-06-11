package com.pemrogramanmobile.apicompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.pemrogramanmobile.apicompose.domain.model.Movie
import com.pemrogramanmobile.apicompose.domain.usecase.GetPopularMoviesUseCase
import com.pemrogramanmobile.apicompose.util.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

data class MovieListUiState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val snackbarMessage: String? = null
)

class MovieViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieListUiState(isLoading = true))
    val uiState: StateFlow<MovieListUiState> = _uiState.asStateFlow()

    init {
        fetchPopularMovies()
    }

    private fun fetchPopularMovies(forceRefresh: Boolean = false) {
        if (forceRefresh || _uiState.value.movies.isEmpty()) {
            _uiState.update { it.copy(isLoading = true, error = null, snackbarMessage = null) }
        }

        getPopularMoviesUseCase()
            .onEach { result ->
                _uiState.update { currentState ->
                    when (result) {
                        is NetworkResult.Success -> {
                            currentState.copy(
                                movies = result.data ?: emptyList(),
                                isLoading = false,
                                error = null,
                                snackbarMessage = null
                            )
                        }
                        is NetworkResult.Error -> {
                            val errorMessage = result.message ?: "Terjadi kesalahan tidak diketahui"
                            if (result.data.isNullOrEmpty()) {
                                currentState.copy(
                                    movies = emptyList(),
                                    isLoading = false,
                                    error = errorMessage,
                                    snackbarMessage = null
                                )
                            } else {
                                currentState.copy(
                                    movies = result.data,
                                    isLoading = false,
                                    error = null,
                                    snackbarMessage = errorMessage
                                )
                            }
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun clearSnackbarMessage() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }

    fun refreshMovies() {
        fetchPopularMovies(forceRefresh = true)
    }
}

class MovieViewModelFactory(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(getPopularMoviesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}