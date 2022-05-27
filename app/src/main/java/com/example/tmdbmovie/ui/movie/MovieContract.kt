package com.example.tmdbmovie.ui.movie

import com.example.domain.model.MovieCategoriesModel
import com.example.domain.model.MovieModel
import com.example.tmdbmovie.base.BasePresenter
import com.example.tmdbmovie.base.BaseView

interface MovieContract {
  interface View : BaseView<Presenter>{
    fun onSuccessGetPopularMovie(data: MovieModel, adapterPosition: Int)
    fun onSuccessGetUpcomingMovie(data: MovieModel, adapterPosition: Int)
    fun onSuccessGetNowPlayingMovie(data: MovieModel, adapterPosition: Int)
    fun onSuccessGetCategories(data: ArrayList<MovieCategoriesModel>)
    fun onErrorException(t: Throwable)
    fun showProgressBar(isShown: Boolean)
    fun setSwipeRefreshing(isFinished: Boolean)
    fun hideErrorHandling()
    fun showErrorHandling(errorIcon: Int, errorMessage: String, errorStatus: String)
  }

  interface Presenter : BasePresenter<View> {
    fun performGetPopularMovie(adapterPosition: Int)
    fun performGetNowPlayingMovie(adapterPosition: Int)
    fun performGetUpcomingMovie(adapterPosition: Int)
    fun populateDummyCategoriesData()
  }
}