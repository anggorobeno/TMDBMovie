package com.example.core.data.source.movie

import com.example.core.data.remote.response.movie.DetailMovieResponse
import com.example.core.data.remote.response.movie.MovieImageResponse
import com.example.core.data.remote.response.movie.MovieResponse
import com.example.core.data.remote.services.TMDBApiService
import io.reactivex.Observable
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(private val apiService: TMDBApiService) :
  MovieDataSource {
  override fun getPopularMovie(currentPagePopular: Int): Observable<MovieResponse> {
    return apiService.getPopularMovie(currentPagePopular)
  }

  override fun getNowPlayingMovie(page: Int): Observable<MovieResponse> {
    return apiService.getNowPlayingMovie(page)
  }

  override fun getUpcomingMovie(currentPageUpcoming: Int): Observable<MovieResponse> {
    return apiService.getUpcomingMovie(currentPageUpcoming)
  }

  override fun getMovieDetail(movieId: Int): Observable<DetailMovieResponse> {
    return apiService.getDetailMovie(movieId)
  }

  override fun getMovieImage(movieId: Int): Observable<MovieImageResponse> {
    return apiService.getMovieImages(movieId)
  }
}