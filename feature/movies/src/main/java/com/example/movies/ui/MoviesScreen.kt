package com.example.movies.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.model.ui.Movie
import com.example.movies.R
import com.example.movies.model.MovieScreenUiState
import com.example.movies.util.MoviePreviewParameter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoviesScreen(movieViewModel: MovieViewModel = hiltViewModel()) {

    val uiState = movieViewModel.uiState.collectAsStateWithLifecycle()

    val refreshing = uiState.value is MovieScreenUiState.Loading
    val refreshingState = rememberPullRefreshState(refreshing, { movieViewModel.getMovies() })

    LaunchedEffect(Unit) { movieViewModel.getMovies() }

    MoviesScreen(uiState.value, refreshing, refreshingState, {
        movieViewModel.getMovies()
    }, {

    })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun MoviesScreen(movieScreenUiState: MovieScreenUiState,
                 refreshing: Boolean,
                 refreshingState: PullRefreshState,
                 onRetry:() -> Unit,
                 onSortToggled: (Boolean) -> Unit) {

    Box(modifier = Modifier.pullRefresh(refreshingState)) {

        when (movieScreenUiState) {
            is MovieScreenUiState.Success -> {
                LazyColumn {
                    items(movieScreenUiState.list) { movie -> Movie(movie) }
                }
            }
            is MovieScreenUiState.Failed -> {
                ErrorText()
            }
            else -> {}
        }

        PullRefreshIndicator(
            refreshing,
            refreshingState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
@Preview
internal fun Movie(@PreviewParameter(MoviePreviewParameter::class) movie: Movie) {
    Column {
        AsyncImage(
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth(),
            model = movie.posterPath,
            contentDescription = movie.posterPath
        )
        Column(modifier = Modifier.padding(8.0.dp)) {
            Text(text = movie.name, style = MaterialTheme.typography.headlineLarge)
            Text(text = movie.firstAirDate)
            Text(text = movie.overview, textAlign = TextAlign.Justify, modifier = Modifier.padding(top = 8.0.dp))

            Divider(thickness = 1.dp, modifier = Modifier.padding(8.0.dp))
        }
    }
}

@Composable
@Preview
internal fun ErrorText() {
    Text(text = stringResource(id = R.string.error_message))
}