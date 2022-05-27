package com.example.domain.model

data class MovieModel(
	val dates: Dates? = null,
	val page: Int? = null,
	val totalPages: Int? = null,
	val results: List<MovieResultModel>? = null,
	val totalResults: Int? = null
)

data class MovieResultModel(
	val overview: String? = null,
	val originalLanguage: String? = null,
	val originalTitle: String? = null,
	val video: Boolean? = null,
	val title: String? = null,
	val genreIds: List<Int?>? = null,
	val posterPath: String? = null,
	val backdropPath: String? = null,
	val releaseDate: String? = null,
	val popularity: Double? = null,
	val voteAverage: Double? = null,
	val id: Int? = null,
	val adult: Boolean? = null,
	val voteCount: Int? = null
)

data class Dates(
	val maximum: String? = null,
	val minimum: String? = null
)

