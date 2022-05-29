package com.example.domain.model

data class UserReviewModel(
	val id: Int? = null,
	val page: Int? = null,
	val totalPages: Int? = null,
	val results: List<ResultsItem>? = null,
	val totalResults: Int? = null
)

data class ResultsItem(
	val authorDetails: AuthorDetails? = null,
	val updatedAt: String? = null,
	val author: String? = null,
	val createdAt: String? = null,
	val id: String? = null,
	val content: String? = null,
	val url: String? = null
)

data class AuthorDetails(
	val avatarPath: String? = null,
	val name: String? = null,
	val rating: Any? = null,
	val username: String? = null
)

