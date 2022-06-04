package com.example.tmdbmovie.ui.movie.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.domain.model.MovieModel
import com.example.domain.model.MovieResultModel
import com.example.tmdbmovie.R
import com.example.tmdbmovie.databinding.ItemMovieContentBinding
import com.example.tmdbmovie.databinding.ViewProgressBarBinding
import com.example.tmdbmovie.databinding.ViewProgressBarFooterBinding
import com.example.tmdbmovie.utils.ConstantUtil.IMAGE_TMDB_BASE_URL
import com.example.tmdbmovie.utils.ConstantUtil.IMAGE_TMDB_POSTER_SIZE_500
import com.example.tmdbmovie.utils.ImageUtil
import javax.inject.Inject

class CommonMovieAdapter @Inject constructor() : RecyclerView.Adapter<ViewHolder>() {
  val TAG = "CommonAdapter"
  var isLoading = false


  override fun getItemViewType(position: Int): Int {
    Log.d(TAG, "getItemViewType: ")
    return when (position) {
      itemCount - 1 -> {
        if (isLoading) {
          FOOTER_TYPE
        } else CONTENT_TYPE
      }

      else -> {
        CONTENT_TYPE
      }
    }
  }

  companion object {
    const val CONTENT_TYPE = 0
    const val FOOTER_TYPE = 1
  }

  interface MovieAdapterListener {
    fun onMovieClicked(id: Int?)
  }

  fun setListener(listener: MovieAdapterListener) {
    this.listener = listener
  }

  private val movieList = arrayListOf<MovieResultModel>()
  private var listener: MovieAdapterListener? = null

  inner class FooterViewHolder(private val binding: ViewProgressBarFooterBinding) :
    ViewHolder(binding.root) {
    init {
      binding.llProgressbarAll.isVisible = true
    }
  }

  inner class PopularMovieViewHolder(private val binding: ItemMovieContentBinding) :
    ViewHolder(binding.root) {
    fun bind(popularMovie: MovieResultModel) {
      val imageUrl = IMAGE_TMDB_BASE_URL + IMAGE_TMDB_POSTER_SIZE_500 + popularMovie.posterPath
      imageUrl.let { posterPath ->
        ImageUtil.loadRoundedImage(
          itemView.context,
          posterPath, binding.ivMovieBanner,
        )
      }
      binding.root.setOnClickListener {
        listener?.onMovieClicked(popularMovie.id)

      }
    }

  }

  fun updatePopularMovieData(data: MovieModel) {
    data.results?.let {
      movieList.addAll(it)
    }
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return when (viewType) {
      CONTENT_TYPE -> {
        val binding =
          ItemMovieContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        PopularMovieViewHolder(binding)
      }
      FOOTER_TYPE -> {
        val binding =
          ViewProgressBarFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        FooterViewHolder(binding)
      }
      else -> {
        val binding =
          ItemMovieContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        PopularMovieViewHolder(binding)
      }
    }

  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    when (holder) {
      is FooterViewHolder -> {
        return Unit
      }
      is PopularMovieViewHolder -> {
        val popularMovie = movieList[position]
        holder.bind(popularMovie)
      }
    }
  }

  override fun getItemCount(): Int {
    return if (isLoading) movieList.size + 1
    else movieList.size
  }
}