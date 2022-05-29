package com.example.tmdbmovie.utils

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

object ConverterUtil {
  fun convertReleasedDate(input: String): String {
    return try {
      val date = LocalDate.parse(input)
      val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
      val formattedDate = date.format(formatter)
      formattedDate
    } catch (e: Exception) {
      e.printStackTrace()
      "N/A"
    }
  }

  fun convertReviewDate(input: String): String {
    return try {
      val date = ZonedDateTime.parse(input)
      val formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm:ss")
      val formattedDate = date.format(formatter)
      formattedDate
    } catch (e: Exception) {
      e.printStackTrace()
      "N/A"
    }
  }

}