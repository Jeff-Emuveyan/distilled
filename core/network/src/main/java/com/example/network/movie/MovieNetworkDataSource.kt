package com.example.network.movie
import com.example.model.response.Response
import javax.inject.Inject

class MovieNetworkDataSource @Inject constructor(private val movieApi: MovieApi): IMovieNetworkDataSource {

    override suspend fun getMovies(pageNumber: Int): Response? {
        return movieApi.getMovies(pageNumber = pageNumber)
    }
}