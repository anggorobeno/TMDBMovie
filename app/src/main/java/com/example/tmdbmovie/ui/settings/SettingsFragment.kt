package com.example.tmdbmovie.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tmdbmovie.BuildConfig
import com.example.tmdbmovie.R
import com.example.tmdbmovie.databinding.FragmentSettingsBinding
import com.example.tmdbmovie.utils.NavigationExt.navigate

class SettingsFragment : Fragment() {
  private var _binding: FragmentSettingsBinding? = null
  private val binding get() = _binding!!
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.contentSettings.apply {
      tvSettingsVersion.text = BuildConfig.VERSION_NAME
      llSettingsSupport.setOnClickListener {
        navigate(R.id.action_settingsFragment_to_aboutFragment)
      }
    }
  }

}