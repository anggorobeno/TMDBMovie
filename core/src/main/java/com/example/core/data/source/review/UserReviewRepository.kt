package com.example.core.data.source.review

import com.example.core.data.remote.response.review.UserReviewResponse
import com.example.domain.model.UserReviewModel
import com.example.domain.repository.movie.UserReviewInterface
import io.reactivex.Observable
import javax.inject.Inject

class UserReviewRepository @Inject constructor(private val remoteSource: UserReviewDataSource) :
  UserReviewInterface {
  override fun getUserReview(movieId: Int): Observable<UserReviewModel> {
    return remoteSource.getUserReview(movieId).map {
      UserReviewResponse.transform(it)
    }
  }
}