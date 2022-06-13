package com.example.tmdbmovie

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tmdbmovie.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  private var _binding: ActivityMainBinding? = null
  private lateinit var navController: NavController
  private val binding get() = _binding!!
  private var menu: Menu? = null
  lateinit var appBarConfiguration: AppBarConfiguration
  override fun onBackPressed() {
    if (navController.currentDestination?.id == R.id.movieFragment ) finish()
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
//        TODO("Add Search Activity Here")
      }
      R.id.settings -> {
        navController.navigate(R.id.action_movieFragment_to_settingsFragment)
      }
      R.id.like -> {
        Toast.makeText(this, "Like Button Clicked", Toast.LENGTH_SHORT).show()
      }
    }
    return true
  }

  fun menuIconVisibility(@IdRes menu: Int, isShown: Boolean) {
    this.menu?.let {
      it.findItem(menu).isVisible = isShown
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen()
    super.onCreate(savedInstanceState)
    _binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
//    setSupportActionBar(binding.viewToolbar.tbMain)
    navController = findNavController(R.id.nav_host_fragment)
    appBarConfiguration = AppBarConfiguration(setOf(R.id.movieFragment))
    //    binding.viewToolbar.tbMain.setupWithNavController(navController, appBarConfiguration)
    fun toolbarVisibility(isShown: Boolean) {
//      binding.viewToolbar.tbMain.isVisible = isShown
    }

    fun toolbarIconVisibility(isHome: Boolean) {
//      val layoutParams = binding.viewToolbar.ivToolbarIcon.layoutParams as Toolbar.LayoutParams
//      if (isHome) {
//        binding.viewToolbar.apply {
//          layoutParams.gravity = Gravity.START
//          ivToolbarIcon.layoutParams = layoutParams
////          ivToolbarGeneralBack.isVisible = false
//        }
//        supportActionBar?.setDisplayShowTitleEnabled(true)
//      } else {
//        binding.viewToolbar.apply {
//          layoutParams.gravity = Gravity.CENTER_HORIZONTAL
//          ivToolbarIcon.layoutParams = layoutParams
////          ivToolbarGeneralBack.isVisible = true
//        }
//        supportActionBar?.setDisplayShowTitleEnabled(false)
//      }
    }

    val listener =
      NavController.OnDestinationChangedListener { _, destination, _ ->
        val destinationFragment =
          setOf(R.id.detailMovieFragment, R.id.settingsFragment, R.id.aboutFragment)
        when {
          destinationFragment.contains(destination.id) -> {
            toolbarIconVisibility(false)
            toolbarVisibility(false)
            menuIconVisibility(R.id.settings, false)
          }
          else -> {
            menuIconVisibility(R.id.settings, true)
            toolbarVisibility(true)
            toolbarIconVisibility(true)
          }
        }
      }
//    navController.addOnDestinationChangedListener(listener)
//    binding.viewToolbar.ivToolbarGeneralBack.setOnClickListener {
//      navController.navigateUp()
//    }
  }
}