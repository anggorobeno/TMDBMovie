package com.example.tmdbmovie.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tmdbmovie.R
import com.example.tmdbmovie.R.string
import com.example.tmdbmovie.databinding.FragmentAboutBinding
import com.example.tmdbmovie.utils.ImageUtil

class AboutFragment: Fragment() {
  var _binding: FragmentAboutBinding? = null
  val binding get() = _binding!!
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentAboutBinding.inflate(layoutInflater,container,false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.contentAbout.apply {
      ImageUtil.loadImage(requireContext(), R.drawable.profile_picture,ivAbout)
      tvEmail.text = getString(string.my_email)
      tvName.text = getString(string.my_name)
    }
  }
}