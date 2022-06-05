package com.example.domain.usecase.movie

import com.example.domain.model.DetailMovieModel
import com.example.domain.model.MovieModel
import io.reactivex.Observable

interface MovieUseCase {
  fun getNowPlayingMovie(page: Int): Observable<MovieModel>
  fun getUpcomingMovie(currentPageUpcoming: Int): Observable<MovieModel>
  fun getPopularMovie(currentPagePopular: Int): Observable<MovieModel>
  fun getMovieDetail(movieId: Int): Observable<DetailMovieModel>
}