package com.example.tmdbmovie.ui.movie.detail

import android.util.Log
import com.example.domain.model.DetailMovieModel
import com.example.domain.model.MovieImageModel
import com.example.domain.model.UserReviewModel
import com.example.domain.usecase.movie.MovieUseCase
import com.example.domain.usecase.review.ReviewUseCase
import com.example.tmdbmovie.ui.movie.detail.DetailMovieContract.View
import com.example.tmdbmovie.utils.RxExtension.applySchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class DetailMoviePresenter @Inject constructor(
  private val movieUseCase: MovieUseCase,
  private val reviewUseCase: ReviewUseCase
) :
  DetailMovieContract.Presenter {

  private var mView: View? = null
  private var mMovieId = 0
  private val mDisposable = CompositeDisposable()
  override fun start() {
    if (mView != null) {
      mView?.setupView()
      mMovieId = mView?.movieId!!
      performGetDetailMovie(mMovieId)
      performGetUserReview(mMovieId)
      performGetMovieImages(mMovieId)
    }
  }

  override fun bind(view: View) {
    mView = view
  }

  override fun unbind() {
    mView = null
    mDisposable.dispose()
  }

  override fun performGetDetailMovie(movieId: Int) {
    mDisposable.add(
      movieUseCase.getMovieDetail(movieId).applySchedulers()
        .subscribeWith(object : DisposableObserver<DetailMovieModel>() {
          override fun onNext(t: DetailMovieModel) {
            mView?.onSuccessGetDetailMovie(t)
          }

          override fun onError(e: Throwable) {
            mView?.onErrorException(e)
          }

          override fun onComplete() {

          }
        })
    )
  }
  override fun performGetMovieImages(movieId: Int) {
    Log.d("TAG", "detailMoviePresenter: ")
    mDisposable.add(
      movieUseCase.getMovieImage(movieId).applySchedulers()
        .subscribeWith(object: DisposableObserver<MovieImageModel>(){
          override fun onNext(t: MovieImageModel) {
            mView?.onSuccessGetMovieImages(t)
          }

          override fun onError(e: Throwable) {
            Log.d("TAG", "onError: ${e.message} ")
            mView?.onErrorException(e)
          }

          override fun onComplete() {

          }
        })
    )
  }
  override fun performGetUserReview(movieId: Int) {
    mDisposable.add(
      reviewUseCase.getUserReview(movieId).applySchedulers()
        .subscribeWith(object : DisposableObserver<UserReviewModel>() {
          override fun onNext(t: UserReviewModel) {
            mView?.onSuccessGetUserReview(t)
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