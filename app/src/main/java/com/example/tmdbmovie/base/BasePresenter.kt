package com.example.tmdbmovie.base

interface BasePresenter<V>  {
  fun start()
  fun bind(view: V)
  fun unbind()
}