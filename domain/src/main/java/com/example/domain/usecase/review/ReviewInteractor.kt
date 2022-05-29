package com.example.domain.usecase.review

import com.example.domain.model.UserReviewModel
import com.example.domain.repository.movie.UserReviewInterface
import io.reactivex.Observable
import javax.inject.Inject

class ReviewInteractor @Inject constructor(private val useCase: UserReviewInterface) :
  ReviewUseCase {
  override fun getUserReview(movieId: Int): Observable<UserReviewModel> {
    return useCase.getUserReview(movieId)
  }
}