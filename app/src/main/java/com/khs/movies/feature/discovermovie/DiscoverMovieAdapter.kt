package com.khs.movies.feature.discovermovie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.khs.movies.R
import com.khs.movies.databinding.ItemMovieBinding
import com.khs.movies.domain.model.movie.DiscoverMovie


class DiscoverMovieAdapter(
    private val onClick: (View, DiscoverMovie) -> Unit
) :
    PagingDataAdapter<DiscoverMovie, DiscoverMovieAdapter.ViewHolder>(MOVIE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    class ViewHolder(
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
    ) {
        private val binding = ItemMovieBinding.bind(itemView)

        fun bind(
            movie: DiscoverMovie?,
            onClick: (View, DiscoverMovie) -> Unit
        ) {
            with(binding) {
                movie?.let {
                    this.movie = movie
                    card.setOnClickListener { onClick(it, movie) }
                }
                executePendingBindings()
            }
        }
    }

    companion object {
        val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<DiscoverMovie>() {
            override fun areItemsTheSame(
                oldItem: DiscoverMovie,
                newItem: DiscoverMovie
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: DiscoverMovie,
                newItem: DiscoverMovie
            ): Boolean = oldItem == newItem
        }
    }
}