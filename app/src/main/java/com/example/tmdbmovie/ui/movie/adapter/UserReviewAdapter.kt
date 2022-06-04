package com.example.tmdbmovie.ui.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.ResultsItem
import com.example.domain.model.UserReviewModel
import com.example.tmdbmovie.R
import com.example.tmdbmovie.databinding.ItemCommentContentBinding
import com.example.tmdbmovie.utils.ConstantUtil
import com.example.tmdbmovie.utils.ConverterUtil
import com.example.tmdbmovie.utils.ImageUtil
import javax.inject.Inject

class UserReviewAdapter @Inject constructor() :
  ListAdapter<ResultsItem, UserReviewAdapter.UserReviewViewHolder>(UserReviewDiffCallback) {
  private val userReviewList = arrayListOf<ResultsItem>()

  inner class UserReviewViewHolder(private val binding: ItemCommentContentBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: ResultsItem) {
      val avatarUrl =
        ConstantUtil.IMAGE_TMDB_BASE_URL + ConstantUtil.IMAGE_TMDB_PROFILE_SIZE_185L + data.authorDetails?.avatarPath
      avatarUrl.let {
        ImageUtil.loadImage(
          itemView.context,
          it,
          binding.ivCommentUser,
          R.drawable.ic_profile
        )
      }

      binding.tvCommentNameDate.text =
        itemView.context.resources.getString(R.string.user_review_name_date, data.author,
          data.updatedAt?.let { ConverterUtil.convertReviewDate(it) })
      binding.tvCommentMessage.text = data.content
    }

  }

  fun updateReview(data: UserReviewModel) {
    data.results?.let {
      userReviewList.addAll(it)
    }
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserReviewViewHolder {
    val binding =
      ItemCommentContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return UserReviewViewHolder(binding)
  }

  override fun onBindViewHolder(holder: UserReviewViewHolder, position: Int) {
//    val data = userReviewList[position]
    holder.bind(getItem(position))

  }

  companion object {
    object UserReviewDiffCallback : DiffUtil.ItemCallback<ResultsItem>() {
      override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
        return oldItem == newItem
      }
    }
  }
}