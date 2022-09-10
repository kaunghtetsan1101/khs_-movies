package com.khs.movies.network.datasourceimpl

import com.khs.movies.data.datasource.discover.DiscoverNetworkDataSource
import com.khs.movies.domain.model.movie.DiscoverMovie
import com.khs.movies.network.api.DiscoverApi
import com.khs.movies.network.mapper.movie.DiscoverMovieJsonMapper
import javax.inject.Inject

class DiscoverNetworkDataSourceImpl @Inject constructor(
    private val discoverApi: DiscoverApi,
    private val discoverMovieJsonMapper: DiscoverMovieJsonMapper
) : DiscoverNetworkDataSource {

    override suspend fun getDiscoverMovies(page: Int, query: String?): List<DiscoverMovie> =
        with(discoverApi) {
            if (query.isNullOrEmpty()){
                getDiscoverMovies(page).results
                    .map(discoverMovieJsonMapper::map)
            } else {
                searchMovies(page,query.trim()).results
                    .map(discoverMovieJsonMapper::map)
            }
        }

}