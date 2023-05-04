package com.example.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    fun provideIoDispatcher(): CoroutineContext {
        return Dispatchers.IO
    }
}