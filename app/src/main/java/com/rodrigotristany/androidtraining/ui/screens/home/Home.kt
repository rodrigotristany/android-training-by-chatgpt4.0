package com.rodrigotristany.androidtraining.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.rodrigotristany.androidtraining.data.Movie
import com.rodrigotristany.androidtraining.data.MoviesRepository
import com.rodrigotristany.androidtraining.ui.theme.AndroidTrainingTheme

@Composable
fun Home(moviesRepository: MoviesRepository) {
    AndroidTrainingTheme {

        val viewModel: HomeViewModel = viewModel { HomeViewModel(moviesRepository) }
        val state by viewModel.state.observeAsState(HomeViewModel.UiState())

        val stateFlow by viewModel.stateFlow.collectAsState()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            if(stateFlow.loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            if(stateFlow.movies.isNotEmpty()) {
                LazyVerticalGrid(columns = GridCells.Adaptive(120.dp)) {
                    items(stateFlow.movies) {movie ->
                        MovieItem(
                            movie = movie,
                            onClick = { viewModel.onMovieClick(movie) })
                    }
                }
            }
        }
    }
}

@Composable
private fun MovieItem(movie: Movie, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w185/${movie.poster_path}",
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2 / 3f)
            )
            if(movie.favorite) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    tint = Color.White
                )
            }
        }
        Text(
            text = movie.title,
            modifier = Modifier.padding(16.dp),
            maxLines = 1
        )
    }
}
