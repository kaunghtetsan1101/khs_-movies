package com.khs.movies.data.datasource.discover

import com.khs.movies.domain.model.movie.DiscoverMovie

interface DiscoverNetworkDataSource {

    suspend fun getDiscoverMovies(page: Int, query : String?): List<DiscoverMovie>
}