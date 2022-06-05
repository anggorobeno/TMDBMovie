package com.example.domain.model

data class MovieImageModel(
	val backdrops: List<BackdropsItem>? = null,
	val posters: List<PostersItem?>? = null,
	val id: Int? = null
)

data class PostersItem(
	val aspectRatio: Double? = null,
	val filePath: String? = null,
	val voteAverage: Double? = null,
	val width: Int? = null,
	val iso6391: String? = null,
	val voteCount: Int? = null,
	val height: Int? = null
)

data class BackdropsItem(
	val aspectRatio: Double? = null,
	val filePath: String? = null,
	val voteAverage: Double? = null,
	val width: Int? = null,
	val iso6391: Any? = null,
	val voteCount: Int? = null,
	val height: Int? = null
)

