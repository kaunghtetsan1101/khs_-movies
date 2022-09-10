package com.khs.movies.domain.usecase.movie

import com.khs.movies.domain.repository.MovieRepository
import javax.inject.Inject

class GetCacheMovie @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int) =
        movieRepository.getCacheMovie(movieId)
}