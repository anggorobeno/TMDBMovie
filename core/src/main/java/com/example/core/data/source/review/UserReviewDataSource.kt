package com.example.core.data.source.review

import com.example.core.data.remote.response.review.UserReviewResponse
import io.reactivex.Observable

interface UserReviewDataSource {
  fun getUserReview(movieId: Int): Observable<UserReviewResponse>
}