package com.khs.movies.domain.usecase.movie

import com.khs.movies.domain.model.movie.Movie
import com.khs.movies.domain.repository.MovieRepository
import javax.inject.Inject

class UpdateCacheMovie @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) =
        movieRepository.updateCacheMovie(movie)
}