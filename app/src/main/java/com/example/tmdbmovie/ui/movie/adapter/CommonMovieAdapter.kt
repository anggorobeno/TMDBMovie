package com.example.tmdbmovie.ui.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.MovieModel
import com.example.domain.model.MovieResultModel
import com.example.tmdbmovie.databinding.ItemMovieContentBinding
import com.example.tmdbmovie.utils.ConstantUtil.IMAGE_TMDB_BASE_URL
import com.example.tmdbmovie.utils.ConstantUtil.IMAGE_TMDB_POSTER_SIZE_500
import com.example.tmdbmovie.utils.ImageUtil

class CommonMovieAdapter : RecyclerView.Adapter<CommonMovieAdapter.PopularMovieViewHolder>() {


  interface MovieAdapterListener {
    fun onMovieClicked(id: Int?)
  }

  fun setListener(listener: MovieAdapterListener){
    this.listener = listener
  }

  private val movieList = arrayListOf<MovieResultModel>()
  private var listener: MovieAdapterListener? = null

  inner class PopularMovieViewHolder(private val binding: ItemMovieContentBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(popularMovie: MovieResultModel) {
      val imageUrl = IMAGE_TMDB_BASE_URL + IMAGE_TMDB_POSTER_SIZE_500 + popularMovie.posterPath
      imageUrl.let { posterPath ->
        ImageUtil.loadRoundedImage(
          itemView.context,
          posterPath, binding.ivMovieBanner
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

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
    val binding =
      ItemMovieContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return PopularMovieViewHolder(binding)
  }

  override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
    val popularMovie = movieList[position]
    holder.bind(popularMovie)
  }

  override fun getItemCount(): Int {
    return movieList.size
  }
}