package com.example.core.data.remote.services

import com.example.core.data.remote.response.movie.DetailMovieResponse
import com.example.core.data.remote.response.movie.MovieResponse
import com.example.core.data.remote.response.review.UserReviewResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface TMDBApiService {
  @GET("movie/popular")
  fun getPopularMovie(): Observable<MovieResponse>

  @GET("movie/now_playing")
  fun getNowPlayingMovie(): Observable<MovieResponse>

  @GET("movie/upcoming")
  fun getUpcomingMovie(): Observable<MovieResponse>

  @GET("movie/{movie_id}")
  fun getDetailMovie(@Path("movie_id") movie_id: Int): Observable<DetailMovieResponse>

  @GET("movie/{movie_id}/reviews")
  fun getUserReview(@Path("movie_id") movie_id: Int): Observable<UserReviewResponse>

}