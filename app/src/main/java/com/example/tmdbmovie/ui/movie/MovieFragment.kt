package com.example.tmdbmovie.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
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
class MovieFragment : Fragment(), MovieContract.View,
  MovieCategoriesAdapter.MovieCategoriesListener {
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

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    presenter.bind(this)
    presenter.start()
    binding.contentMovie.slHomeContainer.setOnRefreshListener {
      hideErrorHandling()
      setSwipeRefreshing(true)
      presenter.start()
    }
  }

  override fun setSwipeRefreshing(isFinished: Boolean) {
    binding.contentMovie.slHomeContainer.isRefreshing = isFinished

  }

  override fun onDestroyView() {
    super.onDestroyView()
    presenter.unbind()
    _binding = null

  }

  override fun setupView() {
    setupAdapter()
  }

  override fun onSuccessGetCategories(data: ArrayList<MovieCategoriesModel>) {
    showProgressBar(false)
    setSwipeRefreshing(false)
    movieCategoriesAdapter.setListener(this)
    movieCategoriesAdapter.updateMovieCategories(5, data)

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
        setupView()

      }
    }

  }

  private fun setupAdapter() {
    binding.apply {
      contentMovie.rvMoviesCategory.layoutManager =
        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
      contentMovie.rvMoviesCategory.adapter = movieCategoriesAdapter
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
    movieCategoriesAdapter.setUpDataPopularMovie(adapterPosition, data)
  }

  override fun onSuccessGetUpcomingMovie(data: MovieModel, adapterPosition: Int) {
    TODO("Not yet implemented")
  }

  override fun onSuccessGetNowPlayingMovie(data: MovieModel, adapterPosition: Int) {
    TODO("Not yet implemented")
  }
}