package com.example.domain.usecase.movie

import com.example.domain.model.DetailMovieModel
import com.example.domain.model.MovieImageModel
import com.example.domain.model.MovieModel
import com.example.domain.repository.movie.MovieRepositoryInterface
import io.reactivex.Observable

class MovieInteractor(private val repository: MovieRepositoryInterface) : MovieUseCase {
  override fun getNowPlayingMovie(page: Int): Observable<MovieModel> {
    return repository.getNowPlayingMovie(page)

  }

  override fun getUpcomingMovie(currentPageUpcoming: Int): Observable<MovieModel> {
    return repository.getUpcomingMovie(currentPageUpcoming)
  }

  override fun getPopularMovie(currentPagePopular: Int): Observable<MovieModel> {
    return repository.getPopularMovie(currentPagePopular)
  }

  override fun getMovieDetail(movieId: Int): Observable<DetailMovieModel> {
    return repository.getMovieDetail(movieId)

  }

  override fun getMovieImage(movieId: Int): Observable<MovieImageModel> {
    return repository.getMovieImage(movieId)
  }
}