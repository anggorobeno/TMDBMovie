package com.example.core.di

import com.example.core.data.remote.services.TMDBApiService
import com.example.core.data.source.movie.MovieDataSource
import com.example.core.data.source.movie.MovieRemoteDataSourceImpl
import com.example.core.data.source.review.UserReviewDataSource
import com.example.core.data.source.review.UserReviewDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Single
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

  @Provides
  @Singleton
  fun provideMovieDataSource(apiService: TMDBApiService): MovieDataSource {
    return MovieRemoteDataSourceImpl(apiService)
  }

  @Provides
  @Singleton
  fun provideUserReviewDataSource(apiService: TMDBApiService): UserReviewDataSource {
    return UserReviewDataSourceImpl(apiService)
  }
}