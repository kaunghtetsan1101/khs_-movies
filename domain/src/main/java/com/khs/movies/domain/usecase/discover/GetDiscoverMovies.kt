package com.khs.movies.domain.usecase.discover

import com.khs.movies.domain.repository.DiscoverRepository
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class GetDiscoverMovies @Inject constructor(
    private val discoverRepository: DiscoverRepository
) {
    operator fun invoke(query : String?,getNetworkDiscoverMovies: GetNetworkDiscoverMovies) =
        discoverRepository.getDiscoverMovies(query,getNetworkDiscoverMovies).distinctUntilChanged()
}