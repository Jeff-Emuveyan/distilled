package com.example.data.repository

import com.example.model.response.Response
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    suspend fun getMovies(pageNumber: Int): Flow<Response?>
}