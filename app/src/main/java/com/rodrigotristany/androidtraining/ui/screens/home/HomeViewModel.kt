package com.rodrigotristany.androidtraining.ui.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigotristany.androidtraining.data.Movie
import com.rodrigotristany.androidtraining.data.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MoviesRepository) : ViewModel() {

    private var _state = MutableLiveData<UiState>()
    val state: LiveData<UiState> = _state

    private val _stateFlow = MutableStateFlow<UiState>(UiState())
    val stateFlow: StateFlow<UiState> = _stateFlow

    init {
        viewModelScope.launch {
            _stateFlow.value = UiState(loading = true)
            repository.requestMovies()

            // repository.movies.collect
            repository.movies.collect {
                _stateFlow.value = UiState(movies = it)
            }
        }
    }

    fun onMovieClick(movie: Movie) {
        viewModelScope.launch {
            repository.updateMovie(movie.copy(favorite = !movie.favorite))
        }
        val movies = _stateFlow.value.movies.toMutableList()
        movies.replaceAll { if(it.id == movie.id) movie.copy(favorite = !movie.favorite) else it }

        _stateFlow.value = _stateFlow.value.copy(movies = movies)
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie> = emptyList()
    )
}