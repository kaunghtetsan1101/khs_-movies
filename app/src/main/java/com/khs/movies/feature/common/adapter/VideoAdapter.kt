package com.khs.movies.feature.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.khs.movies.R
import com.khs.movies.databinding.ItemVideoBinding
import com.khs.movies.feature.common.model.VideoItemUiState
import com.khs.movies.network.api.Api

class VideoAdapter(
    private val onClick: (VideoItemUiState) -> Unit
) : ListAdapter<VideoItemUiState, VideoAdapter.ViewHolder>(VIDEO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    class ViewHolder(
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
    ) {
        private val binding = ItemVideoBinding.bind(itemView)

        fun bind(
            video: VideoItemUiState,
            onClick: (VideoItemUiState) -> Unit
        ) {
            with(binding) {
                Glide.with(root.context)
                    .load(Api.getYoutubeThumbnailPath(video.key))
                    .into(ivThumbnail)
                card.setOnClickListener { onClick(video) }
            }
        }
    }

    companion object {
        val VIDEO_COMPARATOR = object : DiffUtil.ItemCallback<VideoItemUiState>() {
            override fun areItemsTheSame(
                oldItem: VideoItemUiState,
                newItem: VideoItemUiState
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: VideoItemUiState,
                newItem: VideoItemUiState
            ): Boolean = oldItem == newItem
        }
    }
}