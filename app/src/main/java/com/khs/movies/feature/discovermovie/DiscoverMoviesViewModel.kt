package com.khs.movies.feature.discovermovie

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.khs.movies.appbase.core.BasePagingViewModel
import com.khs.movies.domain.model.movie.DiscoverMovie
import com.khs.movies.domain.usecase.discover.GetDiscoverMovies
import com.khs.movies.domain.usecase.discover.GetNetworkDiscoverMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DiscoverMoviesViewModel @Inject constructor(
    private val getNetworkDiscoverMovies: GetNetworkDiscoverMovies,
    private val getDiscoverMovies: GetDiscoverMovies
) : BasePagingViewModel<DiscoverMovie>() {

    val query = MutableLiveData<String?>()

    override fun getPagingDataFlow(queries: String?): Flow<PagingData<DiscoverMovie>> = getDiscoverMovies(queries, getNetworkDiscoverMovies)
}