package com.example.tmdbmovie.ui.movie

import com.example.domain.model.MovieCategoriesModel
import com.example.domain.model.MovieModel
import com.example.domain.usecase.movie.MovieUseCase
import com.example.tmdbmovie.ui.movie.MovieContract.View
import com.example.tmdbmovie.utils.RxExtension.applySchedulers
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class MoviePresenter @Inject constructor(private val useCase: MovieUseCase) :
  MovieContract.Presenter {
  var mView: View? = null
  private val mDisposable = CompositeDisposable()
  override fun performGetPopularMovie(adapterPosition: Int, currentPagePopular: Int) {
    mDisposable.add(
      useCase.getPopularMovie(currentPagePopular)
        .applySchedulers()
        .subscribeWith(object : DisposableObserver<MovieModel>() {
          override fun onNext(t: MovieModel) {
            mView?.onSuccessGetPopularMovie(t, adapterPosition)
          }

          override fun onError(e: Throwable) {
            mView?.onErrorException(e)
          }

          override fun onComplete() {
          }
        })
    )
  }

  override fun performGetNowPlayingMovie(adapterPosition: Int, currentPage: Int) {
    mDisposable.add(
      useCase.getNowPlayingMovie(currentPage)
        .applySchedulers()
        .subscribeWith(object : DisposableObserver<MovieModel>() {
          override fun onNext(t: MovieModel) {
            mView?.onSuccessGetNowPlayingMovie(t, adapterPosition)
          }

          override fun onError(e: Throwable) {
            mView?.onErrorException(e)
          }

          override fun onComplete() {

          }
        })
    )
  }

  override fun performGetUpcomingMovie(adapterPosition: Int, currentPageUpcoming: Int) {
    mDisposable.add(
      useCase.getUpcomingMovie(currentPageUpcoming)
        .applySchedulers()
        .subscribeWith(object : DisposableObserver<MovieModel>() {
          override fun onNext(t: MovieModel) {
            mView?.onSuccessGetUpcomingMovie(t, adapterPosition)
          }

          override fun onError(e: Throwable) {
            mView?.onErrorException(e)
          }

          override fun onComplete() {

          }
        })

    )
  }

  override fun start() {
    if (mView != null) {
      mView?.setupView()
      mView?.showProgressBar(true)
      populateDummyCategoriesData()
    }
  }

  override fun bind(view: View) {
    mView = view
  }

  override fun unbind() {
    mDisposable.dispose()
    mView = null
  }

  override fun populateDummyCategoriesData() {
    val categoriesList: ArrayList<MovieCategoriesModel> = arrayListOf()
    val nowPlaying = MovieCategoriesModel("NowPlaying")
    val popular = MovieCategoriesModel("Popular")
    val upcoming = MovieCategoriesModel("Upcoming")
    categoriesList.add(nowPlaying)
    categoriesList.add(popular)
    categoriesList.add(upcoming)
    mDisposable.add(
      Observable.fromArray(categoriesList)
        .subscribeWith(object : DisposableObserver<ArrayList<MovieCategoriesModel>>() {
          override fun onNext(t: ArrayList<MovieCategoriesModel>) {
            mView?.onSuccessGetCategories(t)
          }

          override fun onError(e: Throwable) {
            mView?.onErrorException(e)
          }

          override fun onComplete() {

          }
        })
    )
  }
}