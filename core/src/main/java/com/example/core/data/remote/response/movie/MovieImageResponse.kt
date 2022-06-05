package com.example.core.data.remote.response.movie

import com.example.domain.model.BackdropsItem
import com.example.domain.model.MovieImageModel
import com.example.domain.model.PostersItem
import com.google.gson.annotations.SerializedName

data class MovieImageResponse(

  @field:SerializedName("backdrops")
  val backdrops: List<BackdropsDto?>? = null,

  @field:SerializedName("posters")
  val posters: List<PosterDto?>? = null,

  @field:SerializedName("id")
  val id: Int? = null
) {
  companion object {
    fun transform(response: MovieImageResponse): MovieImageModel {
      val backdropTransformed = arrayListOf<BackdropsItem>()
      val posterTransformed = arrayListOf<PostersItem>()
      response.backdrops?.forEach {
        backdropTransformed.add(BackdropsDto.transform(it))
      }
      response.posters?.forEach {
        posterTransformed.add(PosterDto.transform(it))
      }
      return MovieImageModel(
        backdrops = backdropTransformed,
        posters = posterTransformed,
        id = response.id
      )
    }
  }
}

data class PosterDto(

  @field:SerializedName("aspect_ratio")
  val aspectRatio: Double? = null,

  @field:SerializedName("file_path")
  val filePath: String? = null,

  @field:SerializedName("vote_average")
  val voteAverage: Double? = null,

  @field:SerializedName("width")
  val width: Int? = null,

  @field:SerializedName("iso_639_1")
  val iso6391: String? = null,

  @field:SerializedName("vote_count")
  val voteCount: Int? = null,

  @field:SerializedName("height")
  val height: Int? = null
) {
  companion object {
    fun transform(dto: PosterDto?): PostersItem {
      return PostersItem(
        voteAverage = dto?.voteAverage,
        width = dto?.width,
        iso6391 = dto?.iso6391,
        voteCount = dto?.voteCount,
        aspectRatio = dto?.aspectRatio,
        height = dto?.height,
        filePath = dto?.filePath
      )
    }
  }
}

data class BackdropsDto(

  @field:SerializedName("aspect_ratio")
  val aspectRatio: Double? = null,

  @field:SerializedName("file_path")
  val filePath: String? = null,

  @field:SerializedName("vote_average")
  val voteAverage: Double? = null,

  @field:SerializedName("width")
  val width: Int? = null,

  @field:SerializedName("iso_639_1")
  val iso6391: Any? = null,

  @field:SerializedName("vote_count")
  val voteCount: Int? = null,

  @field:SerializedName("height")
  val height: Int? = null
) {
  companion object {
    fun transform(dto: BackdropsDto?): BackdropsItem {
      return BackdropsItem(
        dto?.aspectRatio,
        dto?.filePath,
        dto?.voteAverage,
        dto?.width,
        dto?.iso6391,
        dto?.voteCount,
        dto?.height,
      )
    }
  }
}
