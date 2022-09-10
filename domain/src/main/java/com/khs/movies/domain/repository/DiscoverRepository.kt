package com.khs.movies.domain.repository

import androidx.paging.PagingData
import com.khs.movies.domain.model.movie.DiscoverMovie
import com.khs.movies.domain.usecase.discover.GetNetworkDiscoverMovies
import kotlinx.coroutines.flow.Flow

interface DiscoverRepository {

    suspend fun getNetworkDiscoverMovies(page: Int, query : String?): List<DiscoverMovie>

    fun getDiscoverMovies(query : String?,getNetworkDiscoverMovies: GetNetworkDiscoverMovies): Flow<PagingData<DiscoverMovie>>
}