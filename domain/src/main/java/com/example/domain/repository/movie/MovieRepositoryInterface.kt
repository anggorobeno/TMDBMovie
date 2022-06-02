package com.example.domain.repository.movie

import com.example.domain.model.DetailMovieModel
import com.example.domain.model.MovieModel
import com.example.domain.model.MovieResultModel
import dagger.Provides
import io.reactivex.Observable


interface MovieRepositoryInterface {
  fun getPopularMovie(): Observable<MovieModel>
  fun getNowPlayingMovie(page: Int): Observable<MovieModel>
  fun getUpcomingMovie(): Observable<MovieModel>
  fun getMovieDetail(movieId: Int): Observable<DetailMovieModel>
}