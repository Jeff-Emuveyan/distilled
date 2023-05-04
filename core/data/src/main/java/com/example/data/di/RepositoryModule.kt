package com.example.data.di

import com.example.data.repository.IMovieRepository
import com.example.data.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * This module is where the dependency for all repositories (not just IMovieRepository) will be created.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindIMovieRepository(repository: MovieRepository): IMovieRepository
}