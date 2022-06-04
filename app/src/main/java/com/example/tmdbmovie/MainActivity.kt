package com.example.tmdbmovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.tmdbmovie.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  private var _binding: ActivityMainBinding? = null
  private lateinit var navController: NavController
  private val binding get() = _binding!!
  private var menu: Menu? = null

  override fun onBackPressed() {
    if (navController.currentDestination?.id == R.id.movieFragment || navController.currentDestination?.id == R.id.splashScreenFragment) finish()
    else super.onBackPressed()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    this.menu = menu
    val menuInflate = menuInflater
    menuInflate.inflate(R.menu.menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.search -> {
        TODO("Add Search Activity Here")
      }
      R.id.setting -> {
        navController.navigate(R.id.action_movieFragment_to_settingsFragment)
      }
    }
    return true
  }

  private fun menuIconVisibility(@IdRes menu: Int, isShown: Boolean) {
    this.menu?.let {
      it.findItem(menu).isVisible = isShown
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setSupportActionBar(binding.viewToolbar.tbMain)
    navController = findNavController(R.id.nav_host_fragment)
    fun toolbarVisibility(isShown: Boolean) {
      binding.viewToolbar.tbMain.isVisible = isShown
    }

    fun toolbarIconVisibility(isHome: Boolean) {
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
        val destinationFragment = setOf(R.id.detailMovieFragment,R.id.settingsFragment,R.id.aboutFragment)
        val splashScreen = R.id.splashScreenFragment
        val setting = R.id.settingsFragment
        when {
          destinationFragment.contains(destination.id) -> {
            toolbarIconVisibility(false)
            menuIconVisibility(R.id.setting,false)
          }
          destination.id == splashScreen -> {
            toolbarVisibility(false)
          }
          else -> {
            menuIconVisibility(R.id.setting,true)
            toolbarVisibility(true)
            toolbarIconVisibility(true)
          }
        }
      }
    navController.addOnDestinationChangedListener(listener)
    binding.viewToolbar.ivToolbarGeneralBack.setOnClickListener {
      navController.navigateUp()
    }
  }
}