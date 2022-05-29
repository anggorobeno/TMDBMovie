package com.example.core.data.source.movie

import com.example.core.data.remote.response.movie.DetailMovieResponse
import com.example.core.data.remote.response.movie.MovieResponse
import io.reactivex.Observable

interface MovieDataSource {
  fun getPopularMovie(): Observable<MovieResponse>
  fun getNowPlayingMovie(): Observable<MovieResponse>
  fun getUpcomingMovie(): Observable<MovieResponse>
  fun getMovieDetail(movieId: Int): Observable<DetailMovieResponse>
}