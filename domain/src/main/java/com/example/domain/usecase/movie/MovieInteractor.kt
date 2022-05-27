package com.example.domain.usecase.movie

import com.example.domain.model.MovieModel
import com.example.domain.repository.movie.MovieRepositoryInterface
import io.reactivex.Observable

class MovieInteractor(private val repository: MovieRepositoryInterface) : MovieUseCase {
  override fun getNowPlayingMovie(): Observable<MovieModel> {
    return repository.getNowPlayingMovie()

  }

  override fun getUpcomingMovie(): Observable<MovieModel> {
    return repository.getUpcomingMovie()
  }

  override fun getPopularMovie(): Observable<MovieModel> {
    return repository.getPopularMovie()
  }

  override fun getMovieDetail() {

  }
}