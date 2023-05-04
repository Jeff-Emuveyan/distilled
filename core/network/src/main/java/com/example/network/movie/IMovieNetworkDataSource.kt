package com.example.network.movie
import com.example.model.response.Response

interface IMovieNetworkDataSource {
    suspend fun getMovies(pageNumber: Int): Response?
}