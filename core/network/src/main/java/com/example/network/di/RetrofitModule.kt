package com.example.network.di

import com.example.network.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return RetrofitBuilder.getRetrofit()
    }

}