package com.example.tmdbmovie.ui.movie.detail

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.DetailMovieModel
import com.example.domain.model.MovieImageModel
import com.example.domain.model.UserReviewModel
import com.example.tmdbmovie.R
import com.example.tmdbmovie.R.string
import com.example.tmdbmovie.databinding.FragmentDetailMovieBinding
import com.example.tmdbmovie.ui.customview.ResizableCustomView
import com.example.tmdbmovie.ui.movie.adapter.UserReviewAdapter
import com.example.tmdbmovie.ui.movie.detail.banner.MovieBannerAdapter
import com.example.tmdbmovie.utils.ConverterUtil
import com.example.tmdbmovie.utils.EmptyDataObserver
import com.example.tmdbmovie.utils.ImageUtil
import com.faltenreich.skeletonlayout.applySkeleton
import dagger.hilt.android.AndroidEntryPoint
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import okio.IOException
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class DetailMovieFragment : Fragment(), DetailMovieContract.View {
  private val args: DetailMovieFragmentArgs by navArgs()
  private val TAG = "DetailMovieFragment"

  override fun onDestroy() {
    super.onDestroy()
    presenter.unbind()
    _binding = null
  }

  @Inject
  lateinit var presenter: DetailMovieContract.Presenter

  @Inject
  lateinit var userReviewAdapter: UserReviewAdapter

  @Inject
  lateinit var backdropBannerAdapter: MovieBannerAdapter
  private var _binding: FragmentDetailMovieBinding? = null
  private val binding get() = _binding!!
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentDetailMovieBinding.inflate(layoutInflater, container, false)
    return binding.root
  }

  private fun updateUserReviewAdapter(data: UserReviewModel) {
    binding.contentDetailMovie.viewUserReview.apply {
      rvMovie.hideSkeleton()
      tvMovieCategory.ivCategoryMore.isVisible = true
      tvMovieCategory.tvCategoryName.hideSkeleton()
    }
    userReviewAdapter.submitList(data.results)
    if (userReviewAdapter.itemCount <= 0) {
      binding.contentDetailMovie.viewUserReview.apply {
        tvMovieCategory.tvCategoryName.text =
          getString(string.no_review)
        tvMovieCategory.ivCategoryMore.isVisible = false
      }
    }
    userReviewAdapter.registerAdapterDataObserver(
      EmptyDataObserver(
        binding.contentDetailMovie.viewUserReview.rvMovie,
        binding.contentDetailMovie.viewEmptyDataset.rlItemDataSetEmpty
      )
    )

  }

  private fun updateDetailMovie(data: DetailMovieModel) {
    binding.contentDetailMovie.apply {
      showHideSkeleton(false)
      tvMovieOverview.text = data.overview
      ResizableCustomView.doResizeTextView(
        requireContext(),
        tvMovieOverview,
        3, getString(string.all_more),
        tvMovieOverview.maxLines > 3
      )
      tvMovieTitle.text = data.title
      viewMovieGeneralInfo.apply {
        tvReleaseDate.text = data.releaseDate?.let { ConverterUtil.convertReleasedDate(it) }
        tvMovieRuntime.text =
          requireContext().resources.getString(string.movie_runtime, data.runtime)
        tvMovieStatus.text = data.status
      }

    }
  }

  private fun updateDetailBackdrop(data: MovieImageModel) {
    Timber.d("updateDetailBackdrop: $data")
    binding.contentDetailMovie.apply {
      backdropBannerAdapter.updateMovieBanner(data)
      dotsIndicator.setViewPager2(vpMovieBanner)
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
//    (requireActivity() as AppCompatActivity).supportActionBar()
    presenter.bind(this)
    presenter.start()
  }

  private fun showHideSkeleton(isShown: Boolean) {
    if (isShown) {
      binding.contentDetailMovie.apply {
        tvMovieTitle.loadSkeleton(20)
        tvMovieOverview.loadSkeleton(200)
        viewMovieGeneralInfo.viewMovieGeneralInfo.loadSkeleton()
      }
    } else {
      binding.contentDetailMovie.apply {
        tvMovieTitle.hideSkeleton()
        tvMovieOverview.hideSkeleton()
        viewMovieGeneralInfo.viewMovieGeneralInfo.hideSkeleton()
      }
    }
  }

  override fun setupView() {
    val windowManager = requireActivity()
      .getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.defaultDisplay
    val point = Point()
    display.getRealSize(point)
    showHideSkeleton(true)
    binding.contentDetailMovie.apply {
      val layoutParams =
        vpMovieBanner.layoutParams as ConstraintLayout.LayoutParams
      layoutParams.height = (point.x / 16 * 9).toFloat().roundToInt()
      vpMovieBanner.apply {
        this.layoutParams = layoutParams
        adapter = backdropBannerAdapter
      }
      viewUserReview.apply {
        tvMovieCategory.tvCategoryName.text = getString(string.Reviews)
        tvMovieCategory.ivCategoryMore.isVisible = false
        tvMovieCategory.tvCategoryName.loadSkeleton()
        rvMovie.layoutManager =
          LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvMovie.adapter = userReviewAdapter
        rvMovie.loadSkeleton(R.layout.item_comment_content)
      }

    }
  }

  override fun showProgressBar(isShown: Boolean) {
    binding.viewProgressBar.llProgressbarAll.visibility = if (isShown) View.VISIBLE else View.GONE
  }

  override fun setSwipeRefreshing(isFinished: Boolean) {

  }

  override fun hideErrorHandling() {
    binding.apply {
      viewErrorHandling.llErrorHandling.isVisible = false
      contentDetailMovie.clDetailMovieContainer.isVisible = true
    }
  }

  override fun showErrorHandling(errorIcon: Int, errorMessage: String, errorStatus: String) {
    binding.apply {
      viewErrorHandling.llErrorHandling.visibility = View.VISIBLE
      contentDetailMovie.clDetailMovieContainer.visibility = View.GONE

      ImageUtil.loadImage(requireContext(), errorIcon, viewErrorHandling.ivErrorHandlingIcon)
      viewErrorHandling.tvErrorHandlingStatus.text = errorStatus
      viewErrorHandling.tvErrorHandlingMessage.text = errorMessage
      viewErrorHandling.btnErrorHandlingRetry.setOnClickListener {
        hideErrorHandling()
        presenter.start()
      }
    }
  }

  override fun onErrorException(t: Throwable) {
    if (t is IOException) {
      showErrorHandling(
        R.mipmap.ic_offline,
        getString(string.network_error),
        getString(string.all_offline_status)
      )
    }
  }

  override fun onSuccessGetDetailMovie(t: DetailMovieModel) {
    updateDetailMovie(t)
  }

  override fun onSuccessGetMovieImages(data: MovieImageModel) {
    updateDetailBackdrop(data)
  }

  override val movieId: Int
    get() = args.movieId

  override fun onSuccessGetUserReview(data: UserReviewModel) {
    updateUserReviewAdapter(data)
  }
}