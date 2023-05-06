package com.example.data.repository

import com.example.model.response.Response
import com.example.network.movie.IMovieNetworkDataSource
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MovieRepository @Inject constructor(private val ioDispatcher: CoroutineContext,
                                          private val networkDataSource: IMovieNetworkDataSource): IMovieRepository {

    override suspend fun getMovies(pageNumber: Int): Response? = withContext(ioDispatcher) {
        try {
            networkDataSource.getMovies(pageNumber)
        } catch (e: Exception) {
            null
        }
    }
}