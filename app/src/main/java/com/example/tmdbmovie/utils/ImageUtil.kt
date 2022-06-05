package com.example.tmdbmovie.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.tmdbmovie.R

object ImageUtil {
  private fun loadCircularProgress(context: Context): CircularProgressDrawable {
    val drawable = CircularProgressDrawable(context)
    drawable.setColorSchemeColors(R.color.green_turquoish,R.color.green_turquoish,R.color.green_turquoish)
    drawable.centerRadius = 50f
    drawable.strokeWidth = 10f
    drawable.start()
    return drawable
  }

  fun loadRoundedImage(
    context: Context?,
    drawable2: String,
    imageView: ImageView,
    placeholder: Int? = null
  ) {
    var requestOptions = RequestOptions()
    requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(10))
      .error(R.drawable.ic_image_not_found)
      .placeholder(
        loadCircularProgress(context!!)
      )

    Glide.with(context)
      .load(drawable2)
      .apply(requestOptions)
      .into(imageView)
  }

  fun loadImage(context: Context, drawable: Int, imageView: ImageView) {
    Glide.with(context)
      .load(drawable)
      .apply(
        RequestOptions()
          .centerCrop()
          .dontAnimate()
          .dontTransform()
      )
      .into(imageView)
  }

  fun loadImage(context: Context, drawable: String, imageView: ImageView, error: Int? = null) {
    Glide.with(context)
      .load(drawable)
      .apply(
        RequestOptions()
          .centerCrop()
          .dontAnimate()
          .error(error!!)
          .dontTransform()
      )
      .into(imageView)
  }
}