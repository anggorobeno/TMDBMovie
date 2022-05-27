package com.example.tmdbmovie.ui.movie.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.MovieCategoriesModel
import com.example.domain.model.MovieModel
import com.example.domain.model.MovieResultModel
import com.example.tmdbmovie.databinding.ItemCategoriesBinding
import com.skydoves.androidveil.VeilRecyclerFrameView
import javax.inject.Inject
import kotlin.math.log

class MovieCategoriesAdapter @Inject constructor() :
  RecyclerView.Adapter<MovieCategoriesAdapter.MovieCategoriesViewHolder>() {
  private val TAG = "MovieCategoriesAdapter"
  private var mLimit = 3

  companion object {
    // Dummy Categories Data
    const val CATEGORY_NOW_PLAYING = "NowPlaying"
    const val CATEGORY_POPULAR = "Popular"
    const val CATEGORY_UPCOMING = "Upcoming"
  }

  private val categoriesList: ArrayList<MovieCategoriesModel> = arrayListOf()
  private val listViewHolder = arrayListOf<MovieCategoriesViewHolder>()

  lateinit var movieCategoriesListener: MovieCategoriesListener

//  init {
//    val nowPlaying = MovieCategoriesModel("NowPlaying")
//    val popular = MovieCategoriesModel("Popular")
//    val upcoming = MovieCategoriesModel("Upcoming")
//    categoriesList.add(nowPlaying)
//    categoriesList.add(popular)
//    categoriesList.add(upcoming)
//  }

  interface MovieCategoriesListener {
    fun getPopularMovie(adapterPosition: Int)
  }

  var totalCategories: Int = 0

  fun setListener(listener: MovieCategoriesListener) {
    movieCategoriesListener = listener
  }

  fun clearLsit() {
    categoriesList.clear()
  }

  fun updateMovieCategories(totalCategories: Int, categoriesList: ArrayList<MovieCategoriesModel>) {
    listViewHolder.clear()
    this.totalCategories = totalCategories
    this.categoriesList.addAll(categoriesList)
    notifyDataSetChanged()
  }

  inner class MovieCategoriesViewHolder(private val binding: ItemCategoriesBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var skeletonPopularMovieAdapter: VeilRecyclerFrameView? = null
    var popularMovieAdapter: PopularMovieAdapter? = null
    fun bind(categories: MovieCategoriesModel) {

      binding.tvMovieCategory.tvCategoryName.text = categories.categoryName
      when {
        categories.categoryName.equals(CATEGORY_POPULAR, ignoreCase = true) -> {
          setUpPopularMovie()
          movieCategoriesListener.getPopularMovie(adapterPosition)
        }
        categories.categoryName.equals(CATEGORY_NOW_PLAYING,ignoreCase = true) -> {
          setUpPopularMovie()
          movieCategoriesListener.getPopularMovie(adapterPosition)
        }
        categories.categoryName.equals(CATEGORY_UPCOMING,ignoreCase = true) -> {

        }
      }

    }

    private fun setUpPopularMovie() {
      popularMovieAdapter = PopularMovieAdapter()
//      binding.apply {
//        rvMovie.apply {
//          layoutManager =
//            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
//          adapter = popularMovieAdapter
//        }
//      }
      skeletonPopularMovieAdapter = binding.veilRecyclerView.also {
        it.setAdapter(popularMovieAdapter)
        it.setLayoutManager(
          LinearLayoutManager(
            itemView.context,
            LinearLayoutManager.HORIZONTAL,
            false
          )
        )
        it.addVeiledItems(categoriesList.size)
      }
    }

  }

  fun setUpDataPopularMovie(adapterPosition: Int, data: MovieModel) {
    Log.d("TAG", "setUpDataPopularMovie:$adapterPosition ")
    Log.d(TAG, "setUpDataPopularMovie: $listViewHolder")
    listViewHolder[adapterPosition].skeletonPopularMovieAdapter?.unVeil()
//    val list2 = arrayListOf<MovieResultModel>()
//    list2.add(MovieResultModel(posterPath = "http://static.roov.id/upload/images/podcasts/banner/banner-nyayur.jpg"))
//    list2.add(MovieResultModel(posterPath = "http://static.roov.id/upload/images/podcasts/banner/banner-nyayur.jpg"))
//    val list = MovieModel(results = list2)
    listViewHolder[adapterPosition].popularMovieAdapter?.updatePopularMovieData(data)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCategoriesViewHolder {
    val binding = ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return MovieCategoriesViewHolder(binding)
  }

  override fun onBindViewHolder(holder: MovieCategoriesViewHolder, position: Int) {
    val categories = categoriesList[position]
    listViewHolder.add(position,holder)
    holder.bind(categories)
  }

  override fun getItemCount(): Int {
    Log.d("TAG", "getItemCount: ${categoriesList.size} ")
    return if (categoriesList.size < mLimit) categoriesList.size
    else mLimit
  }
}