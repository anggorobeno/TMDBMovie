package com.example.tmdbmovie.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.tmdbmovie.R

object ImageUtil {
  fun loadRoundedImage(context: Context?, drawable: String, imageView: ImageView, placeholder: Int? = null) {
    var requestOptions = RequestOptions()
    requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(10))
      .error(0)

    Glide.with(context!!)
      .load(drawable)
      .apply(requestOptions)
      .into(imageView)
  }
  fun loadImage(context: Context, drawable: Int, imageView: ImageView) {
    Glide.with(context)
      .load(drawable)
      .apply(RequestOptions()
        .centerCrop()
        .dontAnimate()
        .dontTransform())
      .into(imageView)
  }
  fun loadImage(context: Context, drawable: String, imageView: ImageView) {
    Glide.with(context)
      .load(drawable)
      .apply(RequestOptions()
        .centerCrop()
        .dontAnimate()
        .dontTransform())
      .into(imageView)
  }
}