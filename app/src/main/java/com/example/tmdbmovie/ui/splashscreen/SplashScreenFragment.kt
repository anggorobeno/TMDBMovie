package com.example.tmdbmovie.ui.splashscreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tmdbmovie.BuildConfig
import com.example.tmdbmovie.R
import com.example.tmdbmovie.databinding.FragmentSplashscreenBinding

class SplashScreenFragment : Fragment() {
  private var _binding: FragmentSplashscreenBinding? = null
  private val binding get() = _binding!!
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentSplashscreenBinding.inflate(layoutInflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    binding.contentSplashScreen.apply {
      tvSplashVersion.text =
        requireContext().resources.getString(R.string.splash_version, BuildConfig.VERSION_NAME)
      tvSplashAppName.text =
        requireContext().resources.getString(R.string.app_name)
    }
    Handler(Looper.myLooper()!!).postDelayed(
      {
        findNavController().navigate(R.id.action_splashScreenFragment_to_movieFragment)
      }, 500.toLong()
    )

  }
}