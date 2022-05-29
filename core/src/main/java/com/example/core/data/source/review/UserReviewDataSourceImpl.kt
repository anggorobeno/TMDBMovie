package com.example.core.data.source.review

import com.example.core.data.remote.response.review.UserReviewResponse
import com.example.core.data.remote.services.TMDBApiService
import io.reactivex.Observable
import javax.inject.Inject

class UserReviewDataSourceImpl @Inject constructor(private val apiService: TMDBApiService) :
  UserReviewDataSource {
  override fun getUserReview(movieId: Int): Observable<UserReviewResponse> {
    return apiService.getUserReview(movieId)
  }
}