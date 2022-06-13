package com.example.tmdbmovie.ui.movie.adapter

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.domain.model.MovieModel
import com.example.domain.model.MovieResultModel
import com.example.tmdbmovie.databinding.ItemMovieContentBinding
import com.example.tmdbmovie.utils.ConstantUtil.IMAGE_TMDB_BASE_URL
import com.example.tmdbmovie.utils.ConstantUtil.IMAGE_TMDB_POSTER_SIZE_500
import com.example.tmdbmovie.utils.ImageUtil
import com.example.tmdbmovie.utils.movieListener
import koleton.api.generateSkeleton
import koleton.custom.KoletonView
import javax.inject.Inject

class CommonMovieAdapter @Inject constructor() : RecyclerView.Adapter<ViewHolder>() {
  var isLoading = false
    set(value) {
      field = value
      if (value) notifyItemInserted(movieList.size + 1)
      else notifyItemRemoved(movieList.size )
    }

  override fun getItemViewType(position: Int): Int {
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

  private val movieList = arrayListOf<MovieResultModel>()
  private var listener: ((Int) -> Unit) = {}
  fun setMovieListener(movieListener: movieListener) {
    this.listener = movieListener
  }

  inner class FooterViewHolder(binding: KoletonView) :
    ViewHolder(binding) {
    init {
      Handler(Looper.myLooper()!!).postDelayed({
        binding.showSkeleton()
      }, 1500)
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
        popularMovie.id?.let { it1 -> listener.invoke(it1) }
      }
    }

  }

  fun updatePopularMovieData(data: MovieModel) {
    data.results?.let {
      movieList.addAll(it)
    }
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return when (viewType) {
      CONTENT_TYPE -> {
        val binding =
          ItemMovieContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        PopularMovieViewHolder(binding)
      }
      FOOTER_TYPE -> {
        val binding =
          ItemMovieContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        FooterViewHolder(binding.root.generateSkeleton())
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
        return
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

