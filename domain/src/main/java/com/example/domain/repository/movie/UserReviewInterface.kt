package com.example.domain.repository.movie

import com.example.domain.model.UserReviewModel
import io.reactivex.Observable

interface UserReviewInterface {
  fun getUserReview(movieId: Int): Observable<UserReviewModel>
}