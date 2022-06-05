package com.example.core.data.source.movie

import com.example.core.data.remote.response.movie.DetailMovieResponse
import com.example.core.data.remote.response.movie.MovieResponse
import io.reactivex.Observable

interface MovieDataSource {
  fun getPopularMovie(currentPagePopular: Int): Observable<MovieResponse>
  fun getNowPlayingMovie(page: Int): Observable<MovieResponse>
  fun getUpcomingMovie(currentPageUpcoming: Int): Observable<MovieResponse>
  fun getMovieDetail(movieId: Int): Observable<DetailMovieResponse>
}