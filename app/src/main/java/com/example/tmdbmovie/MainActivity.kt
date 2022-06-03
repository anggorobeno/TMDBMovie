package com.example.tmdbmovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.domain.model.MovieModel
import com.example.tmdbmovie.databinding.ActivityMainBinding
import com.example.tmdbmovie.ui.movie.MovieContract
import com.example.tmdbmovie.ui.movie.MovieContract.Presenter
import com.example.tmdbmovie.ui.movie.adapter.MovieCategoriesAdapter.MovieCategoriesListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  private var _binding: ActivityMainBinding? = null
  private lateinit var navController: NavController
  private val binding get() = _binding!!
  override fun onSupportNavigateUp(): Boolean {
    return findNavController(R.id.nav_host_fragment).navigateUp()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setSupportActionBar(binding.viewToolbar.tbMain)
    navController = findNavController(R.id.nav_host_fragment)
    fun toolbarVisibility(isHome: Boolean) {
      val layoutParams = binding.viewToolbar.ivToolbarIcon.layoutParams as Toolbar.LayoutParams
      if (isHome) {
        binding.viewToolbar.apply {
          layoutParams.gravity = Gravity.START
          ivToolbarIcon.layoutParams = layoutParams
          ivToolbarGeneralBack.isVisible = false
        }
        supportActionBar?.setDisplayShowTitleEnabled(true)
      } else {
        binding.viewToolbar.apply {
          layoutParams.gravity = Gravity.CENTER_HORIZONTAL
          ivToolbarIcon.layoutParams = layoutParams
          ivToolbarGeneralBack.isVisible = true
        }
        supportActionBar?.setDisplayShowTitleEnabled(false)
      }
    }

    val listener =
      NavController.OnDestinationChangedListener { _, destination, _ ->
        val destinationFragment = setOf(R.id.detailMovieFragment)
        if (destinationFragment.contains(destination.id)) {
          toolbarVisibility(false)
        } else {
          toolbarVisibility(true)
        }
      }
    navController.addOnDestinationChangedListener(listener)
    binding.viewToolbar.ivToolbarGeneralBack.setOnClickListener {
      onBackPressed()
    }
  }
}