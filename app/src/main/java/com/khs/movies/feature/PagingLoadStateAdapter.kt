package com.khs.movies.feature

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khs.movies.R
import com.khs.movies.databinding.ItemLoadStateBinding
import com.khs.movies.domain.exception.ExceptionToStringMapper
import com.khs.movies.domain.util.mapp


class PagingLoadStateAdapter(
    private val exceptionToStringMapper: ExceptionToStringMapper,
    private val retry: () -> Unit,
) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ) =
        LoadStateViewHolder(exceptionToStringMapper, parent, retry)

    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState,
    ) =
        holder.bind(loadState)
}

class LoadStateViewHolder(
    private val exceptionToStringMapper: ExceptionToStringMapper,
    parent: ViewGroup,
    retry: () -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_load_state, parent, false)
) {
    private val binding = ItemLoadStateBinding.bind(itemView)
    private val progressBar: ProgressBar = binding.pg
    private val errorMsg: TextView = binding.tvError
    private val retry: Button = binding.btnRetry
        .also {
            it.setOnClickListener { retry() }
        }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            errorMsg.text = loadState.error.mapp(exceptionToStringMapper::map)
        }

        progressBar.isVisible = loadState is LoadState.Loading
        retry.isVisible = loadState is LoadState.Error
        errorMsg.isVisible = loadState is LoadState.Error
    }
}