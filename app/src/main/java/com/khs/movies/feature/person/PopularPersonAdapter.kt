package com.khs.movies.feature.person

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.khs.movies.R
import com.khs.movies.databinding.ItemPersonBinding
import com.khs.movies.feature.person.model.PopularPersonItemUiState
import com.khs.movies.network.api.Api

class PopularPersonAdapter(
    private val onClick: (View, PopularPersonItemUiState) -> Unit
) :
    PagingDataAdapter<PopularPersonItemUiState, PopularPersonAdapter.ViewHolder>(PERSON_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    class ViewHolder(
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_person, parent, false)
    ) {
        private val binding = ItemPersonBinding.bind(itemView)

        fun bind(
            person: PopularPersonItemUiState?,
            onClick: (View, PopularPersonItemUiState) -> Unit
        ) {
            person?.let {
                with(binding) {
                    this.person = it
                    it.profilePath?.let { profile ->
                        Glide.with(root.context)
                            .load(Api.getPosterPath(profile))
                            .apply(RequestOptions().circleCrop())
                            .into(ivPerson)
                    }
                    ivPerson.setOnClickListener { onClick(it, person) }
                    executePendingBindings()
                }
            }
        }
    }

    companion object {
        val PERSON_COMPARATOR = object : DiffUtil.ItemCallback<PopularPersonItemUiState>() {
            override fun areItemsTheSame(
                oldItem: PopularPersonItemUiState,
                newItem: PopularPersonItemUiState
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: PopularPersonItemUiState,
                newItem: PopularPersonItemUiState
            ): Boolean = oldItem == newItem
        }
    }
}