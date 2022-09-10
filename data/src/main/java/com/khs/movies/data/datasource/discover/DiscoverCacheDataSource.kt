package com.khs.movies.data.datasource.discover

import androidx.paging.PagingData
import com.khs.movies.domain.model.movie.DiscoverMovie
import com.khs.movies.domain.usecase.discover.GetNetworkDiscoverMovies
import kotlinx.coroutines.flow.Flow

interface DiscoverCacheDataSource {

    fun getDiscoverMovies(query : String?,getNetworkDiscoverMovies: GetNetworkDiscoverMovies): Flow<PagingData<DiscoverMovie>>
}