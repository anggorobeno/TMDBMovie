package com.example.core.data.source.movie

import com.example.core.data.remote.response.movie.DetailMovieResponse
import com.example.core.data.remote.response.movie.MovieResponse
import com.example.domain.model.DetailMovieModel
import com.example.domain.model.MovieModel
import com.example.domain.repository.movie.MovieRepositoryInterface
import io.reactivex.Observable
import javax.inject.Inject

class MovieRepository @Inject constructor(private val remoteDataSource: MovieDataSource) :
  MovieRepositoryInterface {
  override fun getPopularMovie(currentPagePopular: Int): Observable<MovieModel> {
    return remoteDataSource.getPopularMovie(currentPagePopular).map {
      MovieResponse.transform(it)
    }

  }

  override fun getNowPlayingMovie(page: Int): Observable<MovieModel> {
    return remoteDataSource.getNowPlayingMovie(page).map {
      MovieResponse.transform(it)
    }
  }

  override fun getUpcomingMovie(currentPageUpcoming: Int): Observable<MovieModel> {
    return remoteDataSource.getUpcomingMovie(currentPageUpcoming).map {
      MovieResponse.transform(it)
    }
  }

  override fun getMovieDetail(movieId: Int): Observable<DetailMovieModel> {
    return remoteDataSource.getMovieDetail(movieId).map {
      DetailMovieResponse.transform(it)
    }
  }
}