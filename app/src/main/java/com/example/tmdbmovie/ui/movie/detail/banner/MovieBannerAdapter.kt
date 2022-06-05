package com.example.tmdbmovie.ui.movie.detail.banner

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.BackdropsItem
import com.example.domain.model.MovieImageModel
import com.example.tmdbmovie.databinding.ItemMovieBannerBinding
import com.example.tmdbmovie.utils.ConstantUtil
import com.example.tmdbmovie.utils.ImageUtil
import javax.inject.Inject

class MovieBannerAdapter @Inject constructor() :
  RecyclerView.Adapter<MovieBannerAdapter.BannerViewHolder>() {
  private val TAG = "MovieBannerAdapter"
  private val backdropList = arrayListOf<BackdropsItem>()
  private val mLimit = 5

  inner class BannerViewHolder(private val binding: ItemMovieBannerBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(bannerImages: BackdropsItem) {
        ImageUtil.loadRoundedImage(
          itemView.context,
          ConstantUtil.IMAGE_TMDB_BASE_URL + ConstantUtil.IMAGE_TMDB_POSTER_SIZE_780 + bannerImages.filePath,
          binding.ivMovieBanner
        )
    }

  }

  fun updateMovieBanner(data: MovieImageModel) {
    Log.d(TAG, "updateMovieBanner: $data")
    data.backdrops?.let {
      backdropList.addAll(it)
    }
    notifyDataSetChanged()

  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
    val binding = ItemMovieBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return BannerViewHolder(binding)
  }

  override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
    val data = backdropList[position]
    holder.bind(data)
  }

  override fun getItemCount(): Int {
    return if (backdropList.size < mLimit) backdropList.size
    else mLimit
  }
}