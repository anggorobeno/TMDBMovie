package com.example.domain.di

import com.example.domain.repository.movie.MovieRepositoryInterface
import com.example.domain.usecase.movie.MovieInteractor
import com.example.domain.usecase.movie.MovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
  @Provides
  @Singleton
  fun provideMovieInteractor(repository: MovieRepositoryInterface): MovieUseCase {
    return MovieInteractor(repository)
  }

}