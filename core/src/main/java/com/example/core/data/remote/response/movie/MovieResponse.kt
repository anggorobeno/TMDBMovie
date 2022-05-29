package com.example.core.data.remote.response.movie

import com.example.domain.model.Dates
import com.example.domain.model.MovieModel
import com.example.domain.model.MovieResultModel
import com.google.gson.annotations.SerializedName

data class MovieResponse(

  @field:SerializedName("dates")
  val dates: DatesDto? = null,

  @field:SerializedName("page")
  val page: Int? = null,

  @field:SerializedName("total_pages")
  val totalPages: Int? = null,

  @field:SerializedName("results")
  val results: List<MovieResultDto?>? = null,

  @field:SerializedName("total_results")
  val totalResults: Int? = null
) {
  companion object {
    fun transform(response: MovieResponse): MovieModel {
      val list = arrayListOf<MovieResultModel>()
      response.results?.forEach { list.add(MovieResultDto.transform(it)) }
      return MovieModel(
        page = response.page,
        results = list,
        dates = DatesDto.transform(response.dates)
      )
    }

  }

}

data class MovieResultDto(

  @field:SerializedName("overview")
  val overview: String? = null,

  @field:SerializedName("original_language")
  val originalLanguage: String? = null,

  @field:SerializedName("original_title")
  val originalTitle: String? = null,

  @field:SerializedName("video")
  val video: Boolean? = null,

  @field:SerializedName("title")
  val title: String? = null,

  @field:SerializedName("genre_ids")
  val genreIds: List<Int?>? = null,

  @field:SerializedName("poster_path")
  val posterPath: String? = null,

  @field:SerializedName("backdrop_path")
  val backdropPath: String? = null,

  @field:SerializedName("release_date")
  val releaseDate: String? = null,

  @field:SerializedName("popularity")
  val popularity: Double? = null,

  @field:SerializedName("vote_average")
  val voteAverage: Double? = null,

  @field:SerializedName("id")
  val id: Int? = null,

  @field:SerializedName("adult")
  val adult: Boolean? = null,

  @field:SerializedName("vote_count")
  val voteCount: Int? = null
) {
  companion object {
    fun transform(dto: MovieResultDto?): MovieResultModel {
     return dto?.let {
       MovieResultModel(
          overview = it.overview,
          originalLanguage = it.originalLanguage,
          originalTitle = it.originalTitle,
          video = it.video,
          title = it.title,
          genreIds = it.genreIds,
          posterPath = it.posterPath,
          backdropPath = it.backdropPath,
          releaseDate = it.releaseDate,
          popularity = it.popularity,
          voteAverage = it.voteAverage,
          id = it.id,
          adult = it.adult,
          voteCount = it.voteCount
        )
      } ?: MovieResultModel()
    }
  }
}

data class DatesDto(

  @field:SerializedName("maximum")
  val maximum: String? = null,

  @field:SerializedName("minimum")
  val minimum: String? = null
) {
  companion object {
    fun transform(dto: DatesDto?): Dates {
      return dto?.let {
        Dates(
          minimum = it.minimum,
          maximum = it.maximum
        )
      } ?: Dates()
    }
  }
}

