package com.example.data.repository

import com.example.model.response.Response
import com.example.network.movie.IMovieNetworkDataSource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MovieRepository @Inject constructor(private val ioDispatcher: CoroutineContext,
                                          private val networkDataSource: IMovieNetworkDataSource): IMovieRepository {

    override suspend fun getMovies(pageNumber: Int) = flow<Response?> {
        emit(networkDataSource.getMovies(pageNumber))
    }.catch {
        emit(null)
    }.flowOn(ioDispatcher)
}