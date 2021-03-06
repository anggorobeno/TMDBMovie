package com.example.tmdbmovie.ui.movie.detail

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.DetailMovieModel
import com.example.domain.model.UserReviewModel
import com.example.tmdbmovie.R
import com.example.tmdbmovie.R.string
import com.example.tmdbmovie.databinding.FragmentDetailMovieBinding
import com.example.tmdbmovie.ui.movie.adapter.UserReviewAdapter
import com.example.tmdbmovie.utils.ConstantUtil
import com.example.tmdbmovie.utils.ConverterUtil
import com.example.tmdbmovie.utils.ImageUtil
import dagger.hilt.android.AndroidEntryPoint
import okio.IOException
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class DetailMovieFragment : Fragment(), DetailMovieContract.View {
  private val args: DetailMovieFragmentArgs by navArgs()

  override fun onDestroy() {
    super.onDestroy()
    presenter.unbind()
    _binding = null
  }

  @Inject
  lateinit var presenter: DetailMovieContract.Presenter

  @Inject
  lateinit var userReviewAdapter: UserReviewAdapter
  private var _binding: FragmentDetailMovieBinding? = null
  private val binding get() = _binding!!
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentDetailMovieBinding.inflate(layoutInflater, container, false)
    return binding.root
  }

  private fun updateUserReviewAdapter(data: UserReviewModel) {
    userReviewAdapter.updateReview(data)
    if (userReviewAdapter.itemCount <= 0) {
      binding.contentDetailMovie.viewUserReview.apply {
        tvMovieCategory.tvCategoryName.text =
          "No Review Yet"
        tvMovieCategory.ivCategoryMore.isVisible = false
      }
    }
  }

  private fun updateDetailMovie(data: DetailMovieModel) {
    binding.contentDetailMovie.apply {
//      ivPosterImage.hideSkeleton()
//      tvMovieOverview.hideSkeleton()
//      tvMovieTitle.hideSkeleton()
      veilContentDetailMovie.unVeil()
      ImageUtil.loadRoundedImage(
        requireContext(),
        ConstantUtil.IMAGE_TMDB_BASE_URL + ConstantUtil.IMAGE_TMDB_POSTER_SIZE_780 + data.posterPath,
        ivPosterImage
      )
      tvMovieOverview.text = data.overview
      tvMovieTitle.text = data.title
      viewMovieGeneralInfo.apply {
        tvReleaseDate.text = data.releaseDate?.let { ConverterUtil.convertReleasedDate(it) }
        tvMovieRuntime.text =
          requireContext().resources.getString(R.string.movie_runtime, data.runtime)
        tvMovieStatus.text = data.status
      }

    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
//    (requireActivity() as AppCompatActivity).supportActionBar()
    presenter.bind(this)
    presenter.start()
  }

  override fun setupView() {
    val windowManager = requireActivity()
      .getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.defaultDisplay
    val point = Point()
    display.getRealSize(point)

    binding.contentDetailMovie.apply {
      val layoutParams =
        ivPosterImage.layoutParams as ConstraintLayout.LayoutParams
      layoutParams.height = (point.x / 16 * 9).toFloat().roundToInt()
      ivPosterImage.layoutParams = layoutParams
      veilContentDetailMovie.veil()
//      ivPosterImage.loadSkeleton()
//      tvMovieOverview.loadSkeleton(length = 60)
//      tvMovieTitle.loadSkeleton(length = 20)
    }
    binding.contentDetailMovie.viewUserReview.apply {
      tvMovieCategory.tvCategoryName.text = getString(string.Reviews)
      rvMovie.layoutManager =
        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
      rvMovie.adapter = userReviewAdapter
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
        getString(R.string.network_error),
        getString(R.string.all_offline_status)
      )

    }
  }

  override fun onSuccessGetDetailMovie(t: DetailMovieModel) {
    updateDetailMovie(t)
  }

  override val movieId: Int
    get() = args.movieId

  override fun onSuccessGetUserReview(data: UserReviewModel) {
    updateUserReviewAdapter(data)
  }
}