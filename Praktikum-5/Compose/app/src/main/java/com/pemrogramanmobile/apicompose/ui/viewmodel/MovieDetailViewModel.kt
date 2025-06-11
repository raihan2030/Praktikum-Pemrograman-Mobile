package com.pemrogramanmobile.apicompose.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.pemrogramanmobile.apicompose.MyApplication
import com.pemrogramanmobile.apicompose.data.remote.RetrofitClient
import com.pemrogramanmobile.apicompose.data.repository.MovieRepositoryImpl
import com.pemrogramanmobile.apicompose.domain.model.Movie
import com.pemrogramanmobile.apicompose.domain.usecase.GetMovieDetailsUseCase
import com.pemrogramanmobile.apicompose.util.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

data class MovieDetailUiState(
    val movie: Movie? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val snackbarMessage: String? = null
)

class MovieDetailViewModel(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailUiState(isLoading = true))
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    private val movieId: Int = savedStateHandle.get<String>("movieId")?.toIntOrNull() ?: 0

    init {
        if (movieId != 0) {
            fetchMovieDetails(movieId)
        } else {
            _uiState.update {
                it.copy(isLoading = false, error = "Movie ID tidak valid.")
            }
        }
    }

    private fun fetchMovieDetails(id: Int) {
        _uiState.update { it.copy(isLoading = true, error = null, snackbarMessage = null) }

        getMovieDetailsUseCase(id)
            .onEach { result ->
                _uiState.update { currentState ->
                    when (result) {
                        is NetworkResult.Success -> {
                            currentState.copy(
                                movie = result.data,
                                isLoading = false,
                                error = null
                            )
                        }
                        is NetworkResult.Error -> {
                            val errorMessage = result.message ?: "Terjadi kesalahan tidak diketahui"
                            if (currentState.movie != null) {
                                currentState.copy(
                                    isLoading = false,
                                    snackbarMessage = errorMessage
                                )
                            } else {
                                currentState.copy(
                                    movie = null,
                                    isLoading = false,
                                    error = errorMessage
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

    fun retryFetch() {
        if (movieId != 0) {
            fetchMovieDetails(movieId)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as MyApplication
                val savedStateHandle = extras.createSavedStateHandle()

                val movieRepository = MovieRepositoryImpl(
                    RetrofitClient.instance,
                    application.database.movieDao(),
                    application.applicationContext
                )
                val getMovieDetailsUseCase = GetMovieDetailsUseCase(movieRepository)

                return MovieDetailViewModel(
                    getMovieDetailsUseCase,
                    savedStateHandle
                ) as T
            }
        }
    }
}