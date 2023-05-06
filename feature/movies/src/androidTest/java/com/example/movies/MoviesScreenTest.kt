package com.example.movies

import androidx.activity.ComponentActivity
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.model.ui.Movie
import com.example.movies.model.MovieScreenUiState
import com.example.movies.ui.MOVIE_LIST_TAG
import com.example.movies.ui.MoviesScreen
import com.example.movies.ui.PULL_REFRESH_TAG
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MoviesScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun whenMoviesScreenReceivesLoadingUiState_aSwipeRefreshComposableIsShown() {
        composeTestRule.setContent {
            val uiState = MovieScreenUiState.Loading
            val listState = rememberLazyListState()

            val refreshing = true
            val refreshingState = rememberPullRefreshState(refreshing, {})

            MoviesScreen(uiState, listState, refreshing, refreshingState, {}, {})
        }

        // check that the pull/swipe refresh composable is visible:
        composeTestRule.onNodeWithTag(PULL_REFRESH_TAG).assertExists()
        // check that the network-failed composable button is hidden:
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.error_message)).assertDoesNotExist()
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun whenMoviesScreenReceivesFailedUiState_aSwipeRefreshComposableExistsAndErrorButtonExists() {
        composeTestRule.setContent {
            val uiState = MovieScreenUiState.Failed
            val listState = rememberLazyListState()

            val refreshing = false
            val refreshingState = rememberPullRefreshState(refreshing, {})

            MoviesScreen(uiState, listState, refreshing, refreshingState, {}, {})
        }

        // check that the pull/swipe refresh composable is visible so that the user can refresh:
        composeTestRule.onNodeWithTag(PULL_REFRESH_TAG).assertExists()
        // check that the network-failed composable button is hidden
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.error_message)).assertExists()
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun whenMoviesScreenReceivesSuccessUiState_aSwipeRefreshComposableExistsAndErrorButtonDoesNotExists() {
        composeTestRule.setContent {
            val uiState = MovieScreenUiState.Success(getSuccessfulResponse())
            val listState = rememberLazyListState()

            val refreshing = false
            val refreshingState = rememberPullRefreshState(refreshing, {})

            MoviesScreen(uiState, listState, refreshing, refreshingState, {}, {})
        }

        // check that the pull/swipe refresh composable is visible so that the user can refresh:
        composeTestRule.onNodeWithTag(PULL_REFRESH_TAG).assertExists()
        // check that the network-failed composable button is hidden
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.error_message)).assertDoesNotExist()
        // check that the lazy column has two movies displayed on the list:
        composeTestRule.onNodeWithTag(MOVIE_LIST_TAG).onChildren().assertCountEquals(8) // we are using 8
        // because each composable item in our lazy list is made up of four composables ie an async image and three texts,
        // and we have two items added on the list.
    }

    private fun getSuccessfulResponse() = listOf(
        Movie(
        1,
        "Lord of the Rings",
        "Description",
        "www.wwe.com",
        "2020-04-30",
        9.9
                ),
        Movie(
        2,
        "King Kong",
        "Description",
        "www.wwe.com",
        "2020-05-31",
        9.9
                )
            )
}