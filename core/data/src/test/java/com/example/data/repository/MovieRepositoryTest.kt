package com.example.data.repository

import com.example.data.getSuccessfulResponse
import com.example.network.movie.IMovieNetworkDataSource
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class MovieRepositoryTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var movieNetworkDataSource: IMovieNetworkDataSource
    private lateinit var repository: MovieRepository

    @Test
    fun whenMovieRepositoryReceivesSuccessfulResult_theResultIsReturnInTheResponse() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        repository = MovieRepository(dispatcher, movieNetworkDataSource)

        coEvery { movieNetworkDataSource.getMovies(1) } returns getSuccessfulResponse()

        val result = repository.getMovies(1)

        Assert.assertEquals(2, result?.results?.size)
    }

    @Test
    fun whenMovieRepositoryReceivesFailedResult_theFailedResultIsReturnedInTheResponseAsNull() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        repository = MovieRepository(dispatcher, movieNetworkDataSource)

        coEvery { movieNetworkDataSource.getMovies(1) } returns null

        val result = repository.getMovies(1)

        Assert.assertNull(result)
    }
}