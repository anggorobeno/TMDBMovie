package com.example.core.data.source.movie

import com.example.core.data.remote.response.MovieResponse
import com.example.domain.model.MovieModel
import io.reactivex.Observable

interface MovieDataSource {
  fun getPopularMovie(): Observable<MovieResponse>
  fun getNowPlayingMovie(): Observable<MovieResponse>
  fun getUpcomingMovie(): Observable<MovieResponse>
  fun getMovieDetail()
}