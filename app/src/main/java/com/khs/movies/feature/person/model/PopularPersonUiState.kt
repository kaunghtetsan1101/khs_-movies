package com.khs.movies.feature.person.model

import androidx.paging.PagingData
import com.khs.movies.feature.person.model.PopularPersonItemUiState

data class PopularPersonUiState(
    val personPage: PagingData<PopularPersonItemUiState> = PagingData.empty()
) {
    val isEmpty: Boolean
        get() = personPage == PagingData.empty<PopularPersonItemUiState>()
}