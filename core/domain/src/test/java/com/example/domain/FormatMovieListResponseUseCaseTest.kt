package com.example.domain

import com.example.model.response.MovieResponse
import com.example.model.response.Response
import org.junit.Assert.assertEquals
import org.junit.Test

internal class FormatMovieListResponseUseCaseTest {

    private val useCase = FormatMovieListResponseUseCase()

    @Test
    fun whenTheInputContainsMovies_aFormattedListOfTheMoviesIsReturned() {
        val response = getSuccessfulResponseTestData()
        val result = useCase(response)

        assertEquals(2, result.size)
    }

    @Test
    fun whenThereAreNoMoviesInTheInputResponse_anEmptyListIsReturned() {
        val response = Response(null, null)
        val result = useCase(response)

        assertEquals(0, result.size)
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
                    9.9
                ),
                MovieResponse(
                    2,
                    "King Kong",
                    "Description",
                    "www.wwe.com",
                    "2020-05-31",
                    9.9
                )
            )
        )
    }
}