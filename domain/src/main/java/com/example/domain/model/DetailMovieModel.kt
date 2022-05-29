package com.example.domain.model

data class DetailMovieModel(
	val originalLanguage: String? = null,
	val imdbId: String? = null,
	val video: Boolean? = null,
	val title: String? = null,
	val backdropPath: String? = null,
	val revenue: Int? = null,
	val genres: List<GenresModel?>? = null,
	val popularity: Double? = null,
	val productionCountries: List<ProductionCountriesModel?>? = null,
	val id: Int? = null,
	val voteCount: Int? = null,
	val budget: Int? = null,
	val overview: String? = null,
	val originalTitle: String? = null,
	val runtime: Int? = null,
	val posterPath: Any? = null,
	val spokenLanguages: List<SpokenLanguagesModel?>? = null,
	val productionCompanies: List<ProductionCompaniesModel?>? = null,
	val releaseDate: String? = null,
	val voteAverage: Double? = null,
	val belongsToCollection: Any? = null,
	val tagline: String? = null,
	val adult: Boolean? = null,
	val homepage: String? = null,
	val status: String? = null
)

data class ProductionCompaniesModel(
	val logoPath: String? = null,
	val name: String? = null,
	val id: Int? = null,
	val originCountry: String? = null
)

data class GenresModel(
	val name: String? = null,
	val id: Int? = null
)

data class SpokenLanguagesModel(
	val name: String? = null,
	val iso6391: String? = null
)

data class ProductionCountriesModel(
	val iso31661: String? = null,
	val name: String? = null
)

