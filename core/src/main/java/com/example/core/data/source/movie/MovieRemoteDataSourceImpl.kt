package com.example.core.data.source.movie

import com.example.core.data.remote.response.MovieResponse
import com.example.core.data.remote.services.TMDBApiService
import io.reactivex.Observable
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(private val apiService: TMDBApiService): MovieDataSource {
  override fun getPopularMovie(): Observable<MovieResponse> {
    return apiService.getPopularMovie()
  }

  override fun getNowPlayingMovie(): Observable<MovieResponse> {
    return apiService.getNowPlayingMovie()
  }

  override fun getUpcomingMovie(): Observable<MovieResponse> {
    return apiService.getUpcomingMovie()
  }

  override fun getMovieDetail() {
    TODO("Not yet implemented")
  }
}