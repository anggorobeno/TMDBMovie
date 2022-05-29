package com.example.tmdbmovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
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
    val listener = object : NavController.OnDestinationChangedListener {
      override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
      ) {
        val destinationFragment = setOf(R.id.detailMovieFragment)
        if (destinationFragment.contains(destination.id)) {
          binding.viewToolbar.apply {
            val layoutParams = ivToolbarIcon.layoutParams as Toolbar.LayoutParams
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL
            ivToolbarIcon.layoutParams = layoutParams
            ivToolbarGeneralBack.isVisible = true
          }
          supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        else {
          binding.viewToolbar.apply {
            val layoutParams = ivToolbarIcon.layoutParams as Toolbar.LayoutParams
            layoutParams.gravity = Gravity.LEFT
            ivToolbarIcon.layoutParams = layoutParams
            supportActionBar?.setDisplayShowTitleEnabled(true)
            ivToolbarGeneralBack.isVisible = false
          }
        }
      }
    }
    navController.addOnDestinationChangedListener(listener)
    binding.viewToolbar.ivToolbarGeneralBack.setOnClickListener {
      navController.navigateUp()

    }
  }
}