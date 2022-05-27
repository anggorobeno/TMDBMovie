package com.example.core.di

import com.example.core.data.source.movie.MovieDataSource
import com.example.core.data.source.movie.MovieRepository
import com.example.domain.repository.movie.MovieRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

  @Provides
  @Singleton
  fun provideMovieRepository(remoteDataSource: MovieDataSource): MovieRepositoryInterface{
    return MovieRepository(remoteDataSource)
  }
}