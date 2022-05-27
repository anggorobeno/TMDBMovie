package com.example.tmdbmovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.domain.model.MovieModel
import com.example.tmdbmovie.databinding.ActivityMainBinding
import com.example.tmdbmovie.ui.movie.MovieContract
import com.example.tmdbmovie.ui.movie.MovieContract.Presenter
import com.example.tmdbmovie.ui.movie.adapter.MovieCategoriesAdapter.MovieCategoriesListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),MovieCategoriesListener {
  private var _binding: ActivityMainBinding? = null
  private val binding get() = _binding!!
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setSupportActionBar(binding.viewToolbar.tbMain)
    supportActionBar?.setDisplayShowTitleEnabled(false)
  }

  override fun getPopularMovie(adapterPosition: Int) {
    Log.d("TAG", "getPopularMovie: $adapterPosition")
  }
}