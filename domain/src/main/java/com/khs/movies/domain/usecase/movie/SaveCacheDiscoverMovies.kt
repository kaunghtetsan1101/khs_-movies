package com.khs.movies.domain.usecase.movie

import com.khs.movies.domain.model.movie.DiscoverMovie
import com.khs.movies.domain.repository.MovieRepository
import javax.inject.Inject

class SaveCacheDiscoverMovies @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movies: List<DiscoverMovie>) =
        movieRepository.saveCacheDiscoverMovies(movies)
}