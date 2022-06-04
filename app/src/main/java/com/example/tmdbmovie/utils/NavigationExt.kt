package com.example.tmdbmovie.utils

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object NavigationExt {
  fun Fragment.navigate(destination: Int) {
    findNavController().navigate(destination)
  }
}