package com.example.domain.di

import com.example.data.repository.IMovieRepository
import com.example.data.repository.MovieRepository
import com.example.domain.FormatMovieListResponseUseCase
import com.example.domain.IFormatMovieListResponseUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * This module is where the dependency for all use cases (not just IFormatMovieListResponseUseCase) will be created.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class UseCasesModule {

    @Binds
    abstract fun bindIFormatMovieListResponseUseCase(useCase: FormatMovieListResponseUseCase): IFormatMovieListResponseUseCase
}