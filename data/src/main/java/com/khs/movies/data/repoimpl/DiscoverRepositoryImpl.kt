package com.khs.movies.data.repoimpl

import androidx.paging.PagingData
import com.khs.movies.data.datasource.discover.DiscoverCacheDataSource
import com.khs.movies.data.datasource.discover.DiscoverNetworkDataSource
import com.khs.movies.domain.model.movie.DiscoverMovie
import com.khs.movies.domain.repository.DiscoverRepository
import com.khs.movies.domain.usecase.discover.GetNetworkDiscoverMovies
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverRepositoryImpl @Inject constructor(
    private val cacheDataSource: DiscoverCacheDataSource,
    private val networkDataSource: DiscoverNetworkDataSource
) : DiscoverRepository {

    override suspend fun getNetworkDiscoverMovies(page: Int, query : String?): List<DiscoverMovie> =
        networkDataSource.getDiscoverMovies(query = query, page = page)

    override fun getDiscoverMovies(query : String?,getNetworkDiscoverMovies: GetNetworkDiscoverMovies): Flow<PagingData<DiscoverMovie>> =
        cacheDataSource.getDiscoverMovies(query,getNetworkDiscoverMovies)
}