package com.khs.movies.feature.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khs.movies.R
import com.khs.movies.databinding.ItemReviewBinding
import com.khs.movies.feature.common.model.ReviewItemUiState

class ReviewAdapter : ListAdapter<ReviewItemUiState, ReviewAdapter.ViewHolder>(REVIEW_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
    ) {
        private val binding = ItemReviewBinding.bind(itemView)

        fun bind(
            review: ReviewItemUiState
        ) {
            with(binding) {
                tvTitle.text = review.author
                expTvContent.text = review.content
            }
        }
    }

    companion object {
        val REVIEW_COMPARATOR = object : DiffUtil.ItemCallback<ReviewItemUiState>() {
            override fun areItemsTheSame(
                oldItem: ReviewItemUiState,
                newItem: ReviewItemUiState
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ReviewItemUiState,
                newItem: ReviewItemUiState
            ): Boolean = oldItem == newItem
        }
    }
}