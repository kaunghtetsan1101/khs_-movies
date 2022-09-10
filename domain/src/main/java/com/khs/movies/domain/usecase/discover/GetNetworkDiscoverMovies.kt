package com.khs.movies.domain.usecase.discover

import com.khs.movies.domain.repository.DiscoverRepository
import javax.inject.Inject

class GetNetworkDiscoverMovies @Inject constructor(
    private val discoverRepository: DiscoverRepository
) {
    suspend operator fun invoke(query : String?,page: Int) =
        discoverRepository.getNetworkDiscoverMovies(query = query,page = page)
}