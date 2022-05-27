package com.example.tmdbmovie.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.skydoves.androidveil.VeilLayout
import com.skydoves.androidveil.VeilRecyclerFrameView

object SkeletonUtil {
  //  fun <T> performRecyclerViewSkeleton(view: RecyclerView, adapter: T, layoutItemSkeleton: Int): SkeletonScreen {
//    val veilRecyclerView = VeilRecyclerFrameView()
//    return Skeleton.bind(view).adapter(adapter as RecyclerView.Adapter<*>)
//      .shimmer(true).duration(AnimationUtil.LONG_DURATION)
//      .load(layoutItemSkeleton)
//      .show()
//  }
  fun <T> performSkeletonRecyclerView(
    veilRecyclerView: VeilRecyclerFrameView,
    adapter: T?,
    linearLayoutManager: Int?,
    size: Int,
    context: Context
  ) {
    veilRecyclerView.setAdapter(adapter as Adapter<*>)
    veilRecyclerView.addVeiledItems(size)
    veilRecyclerView.setLayoutManager(LinearLayoutManager(context))
  }
}