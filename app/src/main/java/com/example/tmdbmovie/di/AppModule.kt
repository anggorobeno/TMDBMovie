package com.example.tmdbmovie.di

import com.example.domain.usecase.movie.MovieUseCase
import com.example.tmdbmovie.ui.movie.MovieContract
import com.example.tmdbmovie.ui.movie.MoviePresenter
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
  fun provideMoviePresenter(useCase: MovieUseCase): MovieContract.Presenter{
    return MoviePresenter(useCase)
  }
}