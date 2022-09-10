package com.khs.movies.domain.usecase.movie

import com.khs.movies.domain.repository.MovieRepository
import javax.inject.Inject

class DeleteAllCacheMovies @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke() =
        movieRepository.deleteAllCacheMovies()
}