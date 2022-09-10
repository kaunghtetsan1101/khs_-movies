package com.mnm.movies.feature.discovermovie.model

import androidx.paging.PagingData

data class DiscoverMovieUiState(
    val moviePage: PagingData<DiscoverMovieItemUiState> = PagingData.empty()
) {
    val isEmpty: Boolean
        get() = moviePage == PagingData.empty<DiscoverMovieItemUiState>()
}