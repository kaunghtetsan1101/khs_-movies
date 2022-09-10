package com.khs.movies.appbase.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BasePagingViewModel<T : Any> : ViewModel() {
    /**
     * Stream of immutable states representative of the UI.
     */
    val state: StateFlow<UiState<T>>

    /**
     * Processor of side effects from the UI which in turn feedback into [state]
     */
    val doAction: (UiAction) -> Unit

    init {
        val actionStateFlow = MutableSharedFlow<UiAction>()
        val searches = actionStateFlow
            .filterIsInstance<UiAction.Search>()
            .distinctUntilChanged()
            .onStart { emit(UiAction.Search(queries = null)) }
        val queriesScrolled = actionStateFlow
            .filterIsInstance<UiAction.Scroll>()
            .distinctUntilChanged()
            // This is shared to keep the flow "hot" while caching the last query scrolled,
            // otherwise each flatMapLatest invocation would lose the last query scrolled,
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                replay = 1
            )
            .onStart { emit(UiAction.Scroll(currentQueries = null)) }

        state = searches
            .flatMapLatest { search ->
                combine(
                    queriesScrolled,
                    search(queries = search.queries),
                    ::Pair
                )
                    // Each unique PagingData should be submitted once, take the latest from
                    // queriesScrolled
                    .distinctUntilChangedBy { it.second }
                    .map { (scroll, pagingData) ->
                        UiState(
                            queries = search.queries,
                            pagingData = pagingData,
                            lastQueriesScrolled = scroll.currentQueries,
                            // If the search query matches the scroll query, the user has scrolled
                            hasNotScrolledForCurrentSearch = search.queries != scroll.currentQueries
                        )
                    }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = UiState()
            )

        doAction = { action ->
            viewModelScope.launch { actionStateFlow.emit(action) }
        }
    }

    private fun search(queries: String?): Flow<PagingData<T>> =
        getPagingDataFlow(queries)
            .cachedIn(viewModelScope)

    abstract fun getPagingDataFlow(queries: String?): Flow<PagingData<T>>
}

sealed class UiAction {
    data class Search(val queries: String?) : UiAction()
    data class Scroll(val currentQueries: String?) : UiAction()
}

data class UiState<T : Any>(
    val queries: String? = null,
    val lastQueriesScrolled: String? = null,
    val hasNotScrolledForCurrentSearch: Boolean = false,
    val pagingData: PagingData<T> = PagingData.empty()
)