package com.example.tmdbmovie.ui.movie

import com.example.domain.model.MovieCategoriesModel
import com.example.domain.model.MovieModel
import com.example.tmdbmovie.base.BasePresenter
import com.example.tmdbmovie.base.BaseView

interface MovieContract {
  interface View : BaseView{
    fun onSuccessGetPopularMovie(data: MovieModel, adapterPosition: Int)
    fun onSuccessGetUpcomingMovie(data: MovieModel, adapterPosition: Int)
    fun onSuccessGetNowPlayingMovie(data: MovieModel, adapterPosition: Int)
    fun onSuccessGetCategories(data: ArrayList<MovieCategoriesModel>)
  }

  interface Presenter : BasePresenter<View> {
    fun performGetPopularMovie(adapterPosition: Int, currentPagePopular: Int)
    fun performGetNowPlayingMovie(adapterPosition: Int, currentPage: Int)
    fun performGetUpcomingMovie(adapterPosition: Int, currentPageUpcoming: Int)
    fun populateDummyCategoriesData()
  }
}