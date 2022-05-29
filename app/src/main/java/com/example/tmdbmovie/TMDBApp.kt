package com.example.tmdbmovie

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TMDBApp: Application() {
  override fun onCreate() {
    super.onCreate()
    AndroidThreeTen.init(this)
  }

}