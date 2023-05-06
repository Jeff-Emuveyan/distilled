package com.example.movies

import com.example.data.repository.IMovieRepository
import com.example.domain.FormatMovieListResponseUseCase
import com.example.model.response.MovieResponse
import com.example.model.response.Response
import com.example.movies.model.MovieListSortingStyle
import com.example.movies.model.MovieScreenUiState
import com.example.movies.ui.MovieViewModel
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
internal class MovieViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    private var formatMovieListUseCase = FormatMovieListResponseUseCase()
    @MockK
    private lateinit var repository: IMovieRepository

    private lateinit var viewModel: MovieViewModel

    @Test
    fun whenThereIsAnErrorFetchingMovies_theUiStateShouldBeSetToFailedState() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)

        coEvery { repository.getMovies(any()) } returns null

        viewModel = MovieViewModel(dispatcher, formatMovieListUseCase, repository)

        // assert that the ui state = LOADING by default
        assertIs<MovieScreenUiState.Loading>(viewModel.uiState.value)

        viewModel.getMovies(1)

        assertIs<MovieScreenUiState.Failed>(viewModel.uiState.value)
    }

    @Test
    fun whenMoviesAreFetchedSuccessfully_theUiStateShouldBeSetToSuccessState() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)

        coEvery { repository.getMovies(any()) } returns getSuccessfulResponseTestData()

        viewModel = MovieViewModel(dispatcher, formatMovieListUseCase, repository)

        // assert that the ui state = LOADING by default
        assertIs<MovieScreenUiState.Loading>(viewModel.uiState.value)

        viewModel.getMovies(1)

        assertIs<MovieScreenUiState.Success>(viewModel.uiState.value)
    }

    @Test
    fun whenSortStyleIsAlphabetical_moviesListShouldBeSortedAlphabetically() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        viewModel = MovieViewModel(dispatcher, formatMovieListUseCase, repository)

        coEvery { repository.getMovies(any()) } returns getSuccessfulResponseTestData()

        // get the movies
        viewModel.getMovies(1)

        // apply alphabetical sorting:
        viewModel.sort(MovieListSortingStyle.ALPHABETICALLY)

        // get the sorted list
        val sortedList = (viewModel.uiState.value as MovieScreenUiState.Success).list
        // assert
        assertEquals("Anaconda", sortedList[0].name)
        assertEquals("Beauty and the Beast", sortedList[1].name)
        assertEquals("Lord of the Rings", sortedList[2].name)
    }

    @Test
    fun whenSortStyleIsByVoteAverage_moviesListShouldBeSortedByVoteAverage() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        viewModel = MovieViewModel(dispatcher, formatMovieListUseCase, repository)

        coEvery { repository.getMovies(any()) } returns getSuccessfulResponseTestData()

        // get the movies
        viewModel.getMovies(1)

        // apply alphabetical sorting:
        viewModel.sort(MovieListSortingStyle.BY_VOTE_AVERAGE)

        // get the sorted list
        val sortedList = (viewModel.uiState.value as MovieScreenUiState.Success).list
        // assert
        assertEquals("Beauty and the Beast", sortedList[0].name)
        assertEquals("Lord of the Rings", sortedList[1].name)
        assertEquals("Anaconda", sortedList[2].name)
    }

    private fun getSuccessfulResponseTestData(): Response {
        return Response(
            page = 1,
            results = listOf(
                MovieResponse(
                    1,
                    "Lord of the Rings",
                    "Description",
                    "www.wwe.com",
                    "2020-04-30",
                    9.1
                ),
                MovieResponse(
                    2,
                    "Anaconda",
                    "Description",
                    "www.wwe.com",
                    "2020-05-31",
                    0.1
                ),
                MovieResponse(
                    2,
                    "Beauty and the Beast",
                    "Description",
                    "www.wwe.com",
                    "2020-05-31",
                    9.9
                )
            )
        )
    }
}