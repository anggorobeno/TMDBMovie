package com.example.tmdbmovie.di

import com.example.domain.usecase.movie.MovieUseCase
import com.example.domain.usecase.review.ReviewUseCase
import com.example.tmdbmovie.ui.movie.MovieContract
import com.example.tmdbmovie.ui.movie.MoviePresenter
import com.example.tmdbmovie.ui.movie.detail.DetailMovieContract
import com.example.tmdbmovie.ui.movie.detail.DetailMoviePresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(FragmentComponent::class)
class AppModule {
  @Provides
  @FragmentScoped
  fun provideMovieListFragmentPresenter(useCase: MovieUseCase): MovieContract.Presenter {
    return MoviePresenter(useCase)
  }

  @Provides
  @FragmentScoped
  fun provideDetailMovieFragmentPresenter(
    useCase: MovieUseCase,
    reviewUseCase: ReviewUseCase
  ): DetailMovieContract.Presenter {
    return DetailMoviePresenter(useCase, reviewUseCase)
  }

}