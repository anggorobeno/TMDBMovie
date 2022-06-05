package com.example.domain.repository.movie

import com.example.domain.model.DetailMovieModel
import com.example.domain.model.MovieImageModel
import com.example.domain.model.MovieModel
import io.reactivex.Observable

interface MovieRepositoryInterface {
  fun getPopularMovie(currentPagePopular: Int): Observable<MovieModel>
  fun getNowPlayingMovie(page: Int): Observable<MovieModel>
  fun getUpcomingMovie(currentPageUpcoming: Int): Observable<MovieModel>
  fun getMovieDetail(movieId: Int): Observable<DetailMovieModel>
  fun getMovieImage(movieId: Int): Observable<MovieImageModel>
}