package com.khs.movies.appbase.helper

import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khs.movies.appbase.core.UiAction
import com.khs.movies.appbase.core.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

inline fun <T : Any, VH : RecyclerView.ViewHolder> PagingDataAdapter<T, VH>.bindList(
    rv: RecyclerView,
    scope: CoroutineScope,
    uiState: StateFlow<UiState<T>>,
    crossinline onScrollChanged: (UiAction.Scroll) -> Unit,
    crossinline handleUI: suspend (isEmpty: Boolean, isLoading: Boolean, isError: Boolean, errorMsg: String?) -> Unit
) {
    rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (dy != 0) onScrollChanged(UiAction.Scroll(currentQueries = uiState.value.queries))
        }
    })

    val notLoading = loadStateFlow
        // Only emit when REFRESH LoadState for RemoteMediator changes.
        .distinctUntilChangedBy { it.refresh }
        // Only react to cases where Remote REFRESH completes i.e., NotLoading.
        .map { it.refresh is LoadState.NotLoading }

    val hasNotScrolledForCurrentSearch = uiState
        .map { it.hasNotScrolledForCurrentSearch }
        .distinctUntilChanged()

    val shouldScrollToTop = combine(
        notLoading,
        hasNotScrolledForCurrentSearch,
        Boolean::and
    ).distinctUntilChanged()

    val pagingData = uiState
        .map { it.pagingData }
        .distinctUntilChanged()

    scope.launch {
        combine(shouldScrollToTop, pagingData, ::Pair)
            // Each unique PagingData should be submitted once, take the latest from
            // shouldScrollToTop
            .distinctUntilChangedBy { it.second }
            .collectLatest { (shouldScroll, pagingData) ->
                submitData(pagingData)
                // Scroll only after the data has been submitted to the adapter,
                // and is a fresh search
                if (shouldScroll) rv.smoothScrollToPosition(0)
            }
    }

    scope.launch {
        loadStateFlow.collect { loadState ->
            val errorState = loadState.source.refresh as? LoadState.Error
                ?: loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            handleUI(
                loadState.source.refresh is LoadState.NotLoading && itemCount == 0,
                loadState.source.refresh is LoadState.Loading,
                errorState != null,
                errorState?.error?.localizedMessage
            )
        }
    }
}