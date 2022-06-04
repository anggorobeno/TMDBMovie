package com.example.tmdbmovie.ui.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.MovieCategoriesModel
import com.example.domain.model.MovieModel
import com.example.tmdbmovie.R
import com.example.tmdbmovie.databinding.ItemCategoriesBinding
import com.example.tmdbmovie.utils.EndlessRecyclerOnScrollListener
import com.skydoves.androidveil.VeilRecyclerFrameView
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import javax.inject.Inject

class MovieCategoriesAdapter @Inject constructor() :
  RecyclerView.Adapter<MovieCategoriesAdapter.MovieCategoriesViewHolder>() {
  private val TAG = "MovieCategoriesAdapter"
  private var mLimit = 3
  private val viewPool = RecyclerView.RecycledViewPool()

  companion object {
    // Dummy Categories Data
    const val CATEGORY_NOW_PLAYING = "NowPlaying"
    const val CATEGORY_POPULAR = "Popular"
    const val CATEGORY_UPCOMING = "Upcoming"

  }

  private val categoriesList: ArrayList<MovieCategoriesModel> = arrayListOf()
  private val listViewHolder = arrayListOf<MovieCategoriesViewHolder>()

  lateinit var movieCategoriesListener: MovieCategoriesListener

  interface MovieCategoriesListener {
    fun getPopularMovie(adapterPosition: Int)
    fun getNowPlayingMovie(adapterPosition: Int)
    fun getUpcomingMovie(adapterPosition: Int)
    fun goToDetailMovieFragment(movieId: Int)
    fun doLoadMore(adapterPosition: Int, categoryName: String)
  }

  var totalCategories: Int = 0

  fun setListener(listener: MovieCategoriesListener) {
    movieCategoriesListener = listener
  }

  fun clearList() {
    categoriesList.clear()
    listViewHolder.clear()
    notifyDataSetChanged()
  }

  fun updateMovieCategories(totalCategories: Int, categoriesList: ArrayList<MovieCategoriesModel>) {
//    clearList()
    this.totalCategories = totalCategories
    this.categoriesList.addAll(categoriesList)
    notifyDataSetChanged()
  }

  inner class MovieCategoriesViewHolder(val binding: ItemCategoriesBinding) :
    RecyclerView.ViewHolder(binding.root), CommonMovieAdapter.MovieAdapterListener {
    var skeletonPopularMovieAdapter: VeilRecyclerFrameView? = null
    var commonMovieAdapter: CommonMovieAdapter? = null

    init {
      binding.rvMovie.isNestedScrollingEnabled = false
    }

    fun bind(categories: MovieCategoriesModel) {

      binding.tvMovieCategory.tvCategoryName.text = categories.categoryName
      when {
        categories.categoryName.equals(CATEGORY_POPULAR, ignoreCase = true) -> {
          setUpMovie(categories.categoryName)
          movieCategoriesListener.getPopularMovie(adapterPosition)
        }
        categories.categoryName.equals(CATEGORY_NOW_PLAYING, ignoreCase = true) -> {
          setUpMovie(categories.categoryName)
          movieCategoriesListener.getNowPlayingMovie(adapterPosition)
        }
        categories.categoryName.equals(CATEGORY_UPCOMING, ignoreCase = true) -> {
          setUpMovie(categories.categoryName)
          movieCategoriesListener.getUpcomingMovie(adapterPosition)
        }
      }

    }

    private fun setUpMovie(categoryName: String) {
      commonMovieAdapter = CommonMovieAdapter()
      binding.apply {
        rvMovie.apply {
          layoutManager =
            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
          addOnScrollListener(object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
              commonMovieAdapter?.isLoading = true
              movieCategoriesListener.doLoadMore(adapterPosition, categoryName)
            }
          })
          adapter = commonMovieAdapter
        }
      }

      commonMovieAdapter?.setListener(this)
      binding.tvMovieCategory.tvCategoryName.loadSkeleton(length = 15)
      binding.rvMovie.loadSkeleton(R.layout.item_movie_content)
    }

    override fun onMovieClicked(id: Int?) {
      if (id != null) {
        movieCategoriesListener.goToDetailMovieFragment(id)
      }
    }

  }

  fun setUpDataMovie(adapterPosition: Int, data: MovieModel) {
    listViewHolder[adapterPosition].binding.rvMovie.hideSkeleton()
    listViewHolder[adapterPosition].binding.tvMovieCategory.tvCategoryName.hideSkeleton()
    listViewHolder[adapterPosition].binding.rvMovie.setRecycledViewPool(viewPool)
    listViewHolder[adapterPosition].commonMovieAdapter?.isLoading = false
    listViewHolder[adapterPosition].commonMovieAdapter?.updatePopularMovieData(data)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCategoriesViewHolder {
    val binding = ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return MovieCategoriesViewHolder(binding)
  }

  override fun onBindViewHolder(holder: MovieCategoriesViewHolder, position: Int) {
    val categories = categoriesList[position]
    listViewHolder.add(position, holder)
    holder.bind(categories)
  }

  override fun getItemCount(): Int {
    return if (categoriesList.size < mLimit) categoriesList.size
    else mLimit
  }
}