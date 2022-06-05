package com.example.core.data.remote.response.movie

import com.example.domain.model.DetailMovieModel
import com.example.domain.model.GenresModel
import com.example.domain.model.ProductionCompaniesModel
import com.example.domain.model.ProductionCountriesModel
import com.example.domain.model.SpokenLanguagesModel
import com.google.gson.annotations.SerializedName

data class DetailMovieResponse(

  @field:SerializedName("original_language")
  val originalLanguage: String? = null,

  @field:SerializedName("imdb_id")
  val imdbId: String? = null,

  @field:SerializedName("video")
  val video: Boolean? = null,

  @field:SerializedName("title")
  val title: String? = null,

  @field:SerializedName("backdrop_path")
  val backdropPath: String? = null,

  @field:SerializedName("revenue")
  val revenue: Int? = null,

  @field:SerializedName("genres")
  val genres: List<GenreDto?>? = null,

  @field:SerializedName("popularity")
  val popularity: Double? = null,

  @field:SerializedName("production_countries")
  val productionCountries: List<ProductionCountriesDto?>? = null,

  @field:SerializedName("id")
  val id: Int? = null,

  @field:SerializedName("vote_count")
  val voteCount: Int? = null,

  @field:SerializedName("budget")
  val budget: Int? = null,

  @field:SerializedName("overview")
  val overview: String? = null,

  @field:SerializedName("original_title")
  val originalTitle: String? = null,

  @field:SerializedName("runtime")
  val runtime: Int? = null,

  @field:SerializedName("poster_path")
  val posterPath: Any? = null,

  @field:SerializedName("spoken_languages")
  val spokenLanguages: List<SpokenLanguageDto?>? = null,

  @field:SerializedName("production_companies")
  val productionCompanies: List<ProductionCompaniesDto?>? = null,

  @field:SerializedName("release_date")
  val releaseDate: String? = null,

  @field:SerializedName("vote_average")
  val voteAverage: Double? = null,

  @field:SerializedName("belongs_to_collection")
  val belongsToCollection: Any? = null,

  @field:SerializedName("tagline")
  val tagline: String? = null,

  @field:SerializedName("adult")
  val adult: Boolean? = null,

  @field:SerializedName("homepage")
  val homepage: String? = null,

  @field:SerializedName("status")
  val status: String? = null
) {
  companion object {
    fun transform(response: DetailMovieResponse): DetailMovieModel {
      val listGenreTransformed = arrayListOf<GenresModel>()
      response.genres?.forEach {
        listGenreTransformed.add(GenreDto.transform(it))
      }
      return DetailMovieModel(
        originalTitle = response.originalTitle,
        releaseDate = response.releaseDate,
        posterPath = response.posterPath,
        overview = response.overview,
        voteCount = response.voteCount,
        voteAverage = response.voteAverage,
        genres = listGenreTransformed,
        title = response.title,
        status = response.status,
        budget = response.budget,
        runtime = response.runtime
      )

    }
  }
}

data class GenreDto(

  @field:SerializedName("name")
  val name: String? = null,

  @field:SerializedName("id")
  val id: Int? = null
) {
  companion object {
    fun transform(dto: GenreDto?): GenresModel {
      return GenresModel(
        name = dto?.name,
        id = dto?.id
      )

    }
  }
}

data class SpokenLanguageDto(

  @field:SerializedName("name")
  val name: String? = null,

  @field:SerializedName("iso_639_1")
  val iso6391: String? = null
) {
  companion object {
    fun transform(dto: SpokenLanguageDto): SpokenLanguagesModel {
      return SpokenLanguagesModel(
        name = dto.name,
        iso6391 = dto.iso6391
      )
    }
  }
}

data class ProductionCompaniesDto(

  @field:SerializedName("logo_path")
  val logoPath: String? = null,

  @field:SerializedName("name")
  val name: String? = null,

  @field:SerializedName("id")
  val id: Int? = null,

  @field:SerializedName("origin_country")
  val originCountry: String? = null
) {
  companion object {
    fun transform(dto: ProductionCompaniesDto): ProductionCompaniesModel {
      return ProductionCompaniesModel(
        logoPath = dto.logoPath,
        name = dto.name,
        id = dto.id,
        originCountry = dto.originCountry
      )
    }
  }
}

data class ProductionCountriesDto(

  @field:SerializedName("iso_3166_1")
  val iso31661: String? = null,

  @field:SerializedName("name")
  val name: String? = null
) {
  companion object {
    fun transform(dto: ProductionCountriesDto): ProductionCountriesModel {
      return ProductionCountriesModel(
        iso31661 = dto.iso31661,
        name = dto.name
      )

    }
  }

}
