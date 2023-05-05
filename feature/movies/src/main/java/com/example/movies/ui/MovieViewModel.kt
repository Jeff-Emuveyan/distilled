package com.example.movies.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.IMovieRepository
import com.example.domain.FormatMovieListResponseUseCase
import com.example.movies.model.MovieScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val formatMovieListUseCase: FormatMovieListResponseUseCase,
                                         private val repository: IMovieRepository): ViewModel() {

    private val _uiState = MutableStateFlow<MovieScreenUiState>(MovieScreenUiState.Default)
    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        initialValue = MovieScreenUiState.Default)

    fun getMovies(pageNumber: Int = 1) = viewModelScope.launch {
        _uiState.value = MovieScreenUiState.Loading

        when (val result = repository.getMovies(pageNumber)) {
            null -> _uiState.value = MovieScreenUiState.Failed
            else -> _uiState.value = MovieScreenUiState.Success(formatMovieListUseCase(result))
        }
    }
}