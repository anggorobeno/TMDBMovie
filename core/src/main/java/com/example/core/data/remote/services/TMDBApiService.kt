package com.example.core.data.remote.services

import com.example.core.data.remote.response.MovieResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface TMDBApiService {
  @GET("movie/popular")
  fun getPopularMovie(): Observable<MovieResponse>

  @GET("movie/now_playing")
  fun getNowPlayingMovie(): Observable<MovieResponse>

  @GET("movie/upcoming")
  fun getUpcomingMovie(): Observable<MovieResponse>


}