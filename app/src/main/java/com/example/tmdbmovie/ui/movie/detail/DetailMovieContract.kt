package com.example.tmdbmovie.ui.movie.detail

import com.example.domain.model.DetailMovieModel
import com.example.domain.model.UserReviewModel
import com.example.tmdbmovie.base.BasePresenter
import com.example.tmdbmovie.base.BaseView

interface DetailMovieContract {
  interface View : BaseView {
    fun onSuccessGetDetailMovie(t: DetailMovieModel)
    val movieId: Int
    fun onSuccessGetUserReview(data: UserReviewModel)
  }

  interface Presenter : BasePresenter<View> {
    fun performGetDetailMovie(movieId: Int)
    fun performGetUserReview(movieId: Int)
  }
}