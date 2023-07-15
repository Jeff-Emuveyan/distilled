package com.example.movies.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.model.ui.Movie
import com.example.movies.R
import com.example.movies.model.MovieListSortingStyle
import com.example.movies.model.MovieScreenUiState
import com.example.movies.util.MoviePreviewParameter
import kotlinx.coroutines.launch

const val PULL_REFRESH_TAG = "PULL_REFRESH_TAG"
const val MOVIE_LIST_TAG = "MOVIE_LIST_TAG"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoviesScreen(movieViewModel: MovieViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val uiState = movieViewModel.uiState.collectAsStateWithLifecycle()

    val refreshing = uiState.value is MovieScreenUiState.Loading
    val refreshingState = rememberPullRefreshState(refreshing, { movieViewModel.getMovies() })

    LaunchedEffect(Unit) { movieViewModel.getMovies() }

    MoviesScreen(uiState.value, listState, refreshing, refreshingState, { movieViewModel.getMovies()
    }, {
        if(it) {
            movieViewModel.sort(MovieListSortingStyle.ALPHABETICALLY)
            Toast.makeText(context, context.getString(R.string.sorted), Toast.LENGTH_LONG).show()
        } else {
            movieViewModel.sort(MovieListSortingStyle.BY_VOTE_AVERAGE)
            Toast.makeText(context, context.getString(R.string.reverted), Toast.LENGTH_LONG).show()
        }
        coroutineScope.launch { listState.animateScrollToItem(index = 0) }
    })
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
internal fun MoviesScreen(movieScreenUiState: MovieScreenUiState,
                 listState: LazyListState,
                 refreshing: Boolean,
                 refreshingState: PullRefreshState,
                 onRetry:() -> Unit,
                 onSortToggled: (Boolean) -> Unit) {

    Box(modifier = Modifier.pullRefresh(refreshingState).semantics { testTagsAsResourceId = true }) {

        when (movieScreenUiState) {
            is MovieScreenUiState.Success -> {
                Column(modifier = Modifier.semantics {
                    testTagsAsResourceId = true
                }) {
                    Sort(onSort = onSortToggled)
                    LazyColumn(state = listState, modifier = Modifier.semantics {
                        testTagsAsResourceId = true
                        testTag = MOVIE_LIST_TAG
                    }.testTag(MOVIE_LIST_TAG)) {
                        items(
                            movieScreenUiState.list,
                            key = { movie -> movie.id }
                        ) { movie -> Movie(movie) }
                    }
                }
            }
            is MovieScreenUiState.Failed -> { ErrorButton(onRetry) }
            else -> {}
        }

        PullRefreshIndicator(refreshing, refreshingState, modifier = Modifier
                .align(Alignment.TopCenter)
                .testTag(PULL_REFRESH_TAG))
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
            contentDescription = movie.posterPath,
            placeholder = painterResource(R.drawable.image_24)
        )
        Column(modifier = Modifier.padding(8.0.dp)) {
            Text(text = movie.name, style = MaterialTheme.typography.headlineLarge)
            Text(text = movie.firstAirDate)
            Text(text = movie.overview, textAlign = TextAlign.Justify, modifier = Modifier.padding(top = 8.0.dp))

            Divider(thickness = 1.dp, modifier = Modifier.padding(8.0.dp))
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun Sort(onSort:(Boolean) -> Unit) {
    var checked by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(8.0.dp).semantics {
        testTagsAsResourceId = true
        testTag = MOVIE_LIST_TAG
    }) {
        Text(text = stringResource(id = R.string.sort_message))

        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
                onSort(checked)
            }
        )
    }
}

@Composable
internal fun ErrorButton(onRetry:() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExtendedFloatingActionButton(
            onClick = onRetry,
            backgroundColor = MaterialTheme.colorScheme.error,
            icon = { Icon(Icons.Filled.Refresh, stringResource(id = R.string.error_message)) },
            text = { Text(text = stringResource(id = R.string.error_message)) },
        )
    }
}