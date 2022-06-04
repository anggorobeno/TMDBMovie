package com.example.tmdbmovie.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.MovieCategoriesModel
import com.example.domain.model.MovieModel
import com.example.tmdbmovie.R
import com.example.tmdbmovie.databinding.FragmentMovieBinding
import com.example.tmdbmovie.ui.movie.adapter.MovieCategoriesAdapter
import com.example.tmdbmovie.utils.ImageUtil
import dagger.hilt.android.AndroidEntryPoint
import okio.IOException
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment : Fragment(), MovieContract.View,
  MovieCategoriesAdapter.MovieCategoriesListener {
  companion object {
    const val LIMIT_ITEM__PER_PAGE = 20
    const val LIMIT_PAGE = 40
  }

  val startingPage = 1
  var currentPageNowPlaying = startingPage
  var currentPagePopular = startingPage
  var currentPageUpcoming = startingPage

  @Inject
  lateinit var presenter: MovieContract.Presenter
  private var _binding: FragmentMovieBinding? = null
  private val binding get() = _binding!!

  @Inject
  lateinit var movieCategoriesAdapter: MovieCategoriesAdapter
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
    return binding.root
  }

  override fun getPopularMovie(adapterPosition: Int) {
    presenter.performGetPopularMovie(adapterPosition)
  }

  override fun getNowPlayingMovie(adapterPosition: Int) {
    presenter.performGetNowPlayingMovie(adapterPosition, currentPageNowPlaying)
  }

  override fun getUpcomingMovie(adapterPosition: Int) {
    presenter.performGetUpcomingMovie(adapterPosition)
  }

  override fun goToDetailMovieFragment(movieId: Int) {
    val actionToDetailFragment =
      MovieListFragmentDirections.actionMovieFragmentToDetailMovieFragment(movieId)
    findNavController().navigate(actionToDetailFragment)
  }

  override fun doLoadMore(adapterPosition: Int, categoryName: String) {
    when (categoryName) {
      MovieCategoriesAdapter.CATEGORY_NOW_PLAYING -> {
        currentPageNowPlaying++
        if (currentPageNowPlaying <= LIMIT_PAGE) {
          presenter.performGetNowPlayingMovie(
            adapterPosition,
            currentPageNowPlaying
          )
          TODO("add pagination for upcoming and popular movie")
        }
      }
      MovieCategoriesAdapter.CATEGORY_POPULAR -> {
        presenter.performGetPopularMovie(adapterPosition)
      }
      MovieCategoriesAdapter.CATEGORY_UPCOMING -> {
        presenter.performGetUpcomingMovie(adapterPosition)
      }
    }
  }

  private fun resetLoadMore() {
    currentPageNowPlaying = startingPage
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    presenter.bind(this)
    resetLoadMore()
    presenter.start()
    binding.contentMovie.slHomeContainer.setOnRefreshListener {
      hideErrorHandling()
      setSwipeRefreshing(true)
      resetLoadMore()
      presenter.start()
    }
  }

  override fun setSwipeRefreshing(isFinished: Boolean) {
    binding.contentMovie.slHomeContainer.isRefreshing = isFinished

  }

  override fun onDestroyView() {
    super.onDestroyView()

  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.unbind()
    _binding = null
  }

  override fun setupView() {
    setupAdapter()
  }

  override fun onSuccessGetCategories(data: ArrayList<MovieCategoriesModel>) {
    showProgressBar(false)
    setSwipeRefreshing(false)
    movieCategoriesAdapter?.setListener(this)
    movieCategoriesAdapter?.updateMovieCategories(5, data)
  }

  override fun showProgressBar(isShown: Boolean) {
    binding.viewProgressBar.llProgressbarAll.visibility = if (isShown) View.VISIBLE else View.GONE

  }

  override fun hideErrorHandling() {
    binding.apply {
      viewErrorHandling.llErrorHandling.isVisible = false
      contentMovie.slHomeContainer.isVisible = true
    }

  }

  override fun showErrorHandling(errorIcon: Int, errorMessage: String, errorStatus: String) {
    binding.apply {
      viewErrorHandling.llErrorHandling.visibility = View.VISIBLE
      contentMovie.slHomeContainer.visibility = View.GONE
      ImageUtil.loadImage(requireContext(), errorIcon, viewErrorHandling.ivErrorHandlingIcon)
      viewErrorHandling.tvErrorHandlingStatus.text = errorStatus
      viewErrorHandling.tvErrorHandlingMessage.text = errorMessage
      viewErrorHandling.btnErrorHandlingRetry.setOnClickListener {
        hideErrorHandling()
        presenter.start()
      }
    }

  }

  private fun setupAdapter() {
    binding.contentMovie.apply {
      rvMoviesCategory.layoutManager =
        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
      rvMoviesCategory.adapter = movieCategoriesAdapter
    }
  }

  override fun onErrorException(t: Throwable) {
    if (t is IOException) {
      showErrorHandling(
        R.mipmap.ic_offline,
        getString(R.string.network_error),
        getString(R.string.all_offline_status)
      )

    }

  }

  override fun onSuccessGetPopularMovie(data: MovieModel, adapterPosition: Int) {
    movieCategoriesAdapter.setUpDataMovie(adapterPosition, data)
  }

  override fun onSuccessGetUpcomingMovie(data: MovieModel, adapterPosition: Int) {
    movieCategoriesAdapter.setUpDataMovie(adapterPosition, data)
  }

  override fun onSuccessGetNowPlayingMovie(data: MovieModel, adapterPosition: Int) {
    movieCategoriesAdapter.setUpDataMovie(adapterPosition, data)
  }
}