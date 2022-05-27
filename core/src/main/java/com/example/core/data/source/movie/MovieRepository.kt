package com.example.core.data.source.movie

import com.example.core.data.remote.response.MovieResponse
import com.example.domain.model.MovieModel
import com.example.domain.repository.movie.MovieRepositoryInterface
import dagger.Provides
import io.reactivex.Observable
import javax.inject.Inject

class MovieRepository @Inject constructor(private val remoteDataSource: MovieDataSource): MovieRepositoryInterface {
  override fun getPopularMovie(): Observable<MovieModel> {
    return remoteDataSource.getPopularMovie().map {
      MovieResponse.transform(it)
    }

  }

  override fun getNowPlayingMovie(): Observable<MovieModel> {
    return remoteDataSource.getNowPlayingMovie().map {
      MovieResponse.transform(it)
    }
  }

  override fun getUpcomingMovie(): Observable<MovieModel> {
    return remoteDataSource.getUpcomingMovie().map {
      MovieResponse.transform(it)
    }
  }

  override fun getMovieDetail() {
    TODO("Not yet implemented")
  }
}