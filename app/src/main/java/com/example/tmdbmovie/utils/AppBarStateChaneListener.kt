package com.example.tmdbmovie.utils

import com.example.tmdbmovie.utils.AppBarStateChangeListener.State.COLLAPSED
import com.example.tmdbmovie.utils.AppBarStateChangeListener.State.EXPANDED
import com.example.tmdbmovie.utils.AppBarStateChangeListener.State.IDLE
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener

abstract class AppBarStateChangeListener : OnOffsetChangedListener {
  enum class State {
    EXPANDED,
    COLLAPSED,
    IDLE
  }

  private var mCurrentState = IDLE
  override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
    mCurrentState = if (i == 0) {
      if (mCurrentState != EXPANDED) {
        onStateChanged(appBarLayout, EXPANDED)
      }
      EXPANDED
    } else if (Math.abs(i) >= appBarLayout.totalScrollRange) {
      if (mCurrentState != COLLAPSED) {
        onStateChanged(appBarLayout, COLLAPSED)
      }
      COLLAPSED
    } else {
      if (mCurrentState != IDLE) {
        onStateChanged(appBarLayout, IDLE)
      }
      IDLE
    }
  }

  abstract fun onStateChanged(appBarLayout: AppBarLayout?, state: State?)
}