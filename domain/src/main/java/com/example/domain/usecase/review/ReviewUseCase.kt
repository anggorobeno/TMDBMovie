package com.example.domain.usecase.review

import com.example.domain.model.UserReviewModel
import io.reactivex.Observable

interface ReviewUseCase {
  fun getUserReview(movieId: Int): Observable<UserReviewModel>
}