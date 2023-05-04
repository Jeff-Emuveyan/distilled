package com.example.network.di

import com.example.model.response.Response
import com.example.network.movie.MovieApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Inject

class MovieApiImpl @Inject constructor(private val retrofit: Retrofit): MovieApi {

    override suspend fun getMovies(apiKey: String, language: String, pageNumber: Int): Response? {
        return retrofit.create(MovieApi::class.java).getMovies(apiKey, language, pageNumber)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieApiModule {

    @Binds
    abstract fun bindApi(apiImpl: MovieApiImpl): MovieApi

}