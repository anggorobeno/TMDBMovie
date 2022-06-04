package com.example.tmdbmovie.base

interface BaseView  {
  fun setupView()
  fun showProgressBar(isShown: Boolean)
  fun setSwipeRefreshing(isFinished: Boolean)
  fun hideErrorHandling()
  fun showErrorHandling(errorIcon: Int, errorMessage: String, errorStatus: String)
  fun onErrorException(t: Throwable)
}