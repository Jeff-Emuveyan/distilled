package com.example.movies.model

import com.example.model.ui.Movie

sealed class MovieScreenUiState {
    object Loading: MovieScreenUiState()
    object Failed: MovieScreenUiState()
    class Success(val list: List<Movie>): MovieScreenUiState()
}