package com.pemrogramanmobile.apicompose.ui.screen.movielist

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pemrogramanmobile.apicompose.MyApplication
import com.pemrogramanmobile.apicompose.R
import com.pemrogramanmobile.apicompose.data.remote.RetrofitClient
import com.pemrogramanmobile.apicompose.data.repository.MovieRepositoryImpl
import com.pemrogramanmobile.apicompose.domain.usecase.GetPopularMoviesUseCase
import com.pemrogramanmobile.apicompose.ui.Routes
import com.pemrogramanmobile.apicompose.ui.theme.ApiComposeTheme
import com.pemrogramanmobile.apicompose.ui.viewmodel.MovieViewModel
import com.pemrogramanmobile.apicompose.ui.viewmodel.MovieViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(navController: NavController) {
    val title = stringResource(R.string.title)

    val context = LocalContext.current.applicationContext
    val movieRepositoryImpl = remember(context) {
        MovieRepositoryImpl(
            tmdbApiService = RetrofitClient.instance,
            movieDao = (context as MyApplication).database.movieDao(),
            context = context
        )
    }
    val getPopularMoviesUseCase = remember(movieRepositoryImpl) {
        GetPopularMoviesUseCase(movieRepositoryImpl)
    }
    val movieViewModel: MovieViewModel = viewModel(
        factory = MovieViewModelFactory(getPopularMoviesUseCase)
    )

    val uiState by movieViewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
            movieViewModel.clearSnackbarMessage()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(title) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = { movieViewModel.refreshMovies() }) {
                        Icon(
                            Icons.Filled.Refresh,
                            contentDescription = "Refresh Film",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (uiState.isLoading && uiState.movies.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            else if (uiState.error != null && uiState.movies.isEmpty()) {
                Column(
                    modifier = Modifier.align(Alignment.Center).padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Oops! ${uiState.error}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { movieViewModel.refreshMovies() }) {
                        Text(stringResource(R.string.try_again))
                    }
                }
            }
            else if (uiState.movies.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(uiState.movies, key = { movie -> movie.id }) { movie ->
                        MovieItem(
                            movie = movie,
                            modifier = Modifier.fillMaxWidth(),
                            onDetailsClick = { movieId ->
                                navController.navigate(Routes.navigateById(movieId))
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
            else if (!uiState.isLoading && uiState.error == null) {
                Text(
                    text = stringResource(R.string.no_film_found),
                    modifier = Modifier.align(Alignment.Center).padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (uiState.isLoading && uiState.movies.isNotEmpty()) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter))
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun MovieListScreenLightPreview() {
    ApiComposeTheme {
        Surface {
            MovieListScreen(navController = rememberNavController())
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieListScreenDarkPreview() {
    ApiComposeTheme {
        Surface {
            MovieListScreen(navController = rememberNavController())
        }
    }
}