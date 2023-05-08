package com.example.movies.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.IMovieRepository
import com.example.domain.FormatMovieListResponseUseCase
import com.example.domain.IFormatMovieListResponseUseCase
import com.example.model.ui.Movie
import com.example.movies.model.MovieListSortingStyle
import com.example.movies.model.MovieScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MovieViewModel @Inject constructor(private val ioDispatcher: CoroutineContext,
                                         private val formatMovieListUseCase: IFormatMovieListResponseUseCase,
                                         private val repository: IMovieRepository): ViewModel() {

    private val _uiState = MutableStateFlow<MovieScreenUiState>(MovieScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getMovies(pageNumber: Int = 1) = viewModelScope.launch(ioDispatcher) {
        _uiState.value = MovieScreenUiState.Loading

        when (val result = repository.getMovies(pageNumber)) {
            null -> _uiState.value = MovieScreenUiState.Failed
            else -> _uiState.value = MovieScreenUiState.Success(formatMovieListUseCase(result))
        }
    }

    fun sort(sortingStyle: MovieListSortingStyle) = viewModelScope.launch(ioDispatcher) {
        if (_uiState.value is MovieScreenUiState.Success) {

            val sortedList: List<Movie> = when (sortingStyle) {
                MovieListSortingStyle.ALPHABETICALLY -> {
                    (_uiState.value as MovieScreenUiState.Success).list.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.name })
                }
                MovieListSortingStyle.BY_VOTE_AVERAGE -> {
                    (_uiState.value as MovieScreenUiState.Success).list.sortedBy { it.voteAverage }.reversed()
                }
            }

            _uiState.update { MovieScreenUiState.Success(sortedList) }
        }
    }
}